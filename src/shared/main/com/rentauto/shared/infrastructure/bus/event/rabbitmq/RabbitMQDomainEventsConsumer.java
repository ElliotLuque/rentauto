package com.rentauto.shared.infrastructure.bus.event.rabbitmq;

import com.rentauto.shared.domain.bus.event.DomainEvent;
import com.rentauto.shared.infrastructure.bus.event.DomainEventSubscribersInformation;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public final class RabbitMQDomainEventsConsumer {
    private final ConnectionFactory connectionFactory;
    private final RabbitAdmin rabbitAdmin;
    private final DomainEventSubscribersInformation subscribers;
    private final String exchangeName;
    private final String deadLetterExchangeName;
    private final ApplicationContext context;
    private final RabbitMQMessageConsumer messageConsumer;
    private SimpleMessageListenerContainer container;

    public RabbitMQDomainEventsConsumer(
            ConnectionFactory connectionFactory,
            RabbitAdmin rabbitAdmin,
            DomainEventSubscribersInformation subscribers,
            ApplicationContext context,
            RabbitMQMessageConsumer messageConsumer,
            @Value("${domain-event-bus.exchange}") String exchangeName,
            @Value("${domain-event-bus.dead-letter-exchange}") String deadLetterExchangeName
    ) {
        this.connectionFactory = connectionFactory;
        this.rabbitAdmin = rabbitAdmin;
        this.subscribers = subscribers;
        this.context = context;
        this.messageConsumer = messageConsumer;
        this.exchangeName = exchangeName;
        this.deadLetterExchangeName = deadLetterExchangeName;
    }

    @PostConstruct
    public void setUp() {
        TopicExchange exchange = new TopicExchange(exchangeName, true, false);
        TopicExchange deadLetterExchange = new TopicExchange(deadLetterExchangeName, true, false);

        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareExchange(deadLetterExchange);

        List<String> queueNames = new ArrayList<>();

        for (Class<?> subscriberClass : subscribers.all()) {
            String queueName = subscribers.formatQueueName(subscribers.queueNameFor(subscriberClass));
            String deadLetterQueueName = String.format("%s.dlq", queueName);

            // Dead letter queue
            Map<String, Object> deadLetterArgs = new HashMap<>();
            Queue deadLetterQueue = QueueBuilder.durable(deadLetterQueueName)
                    .withArguments(deadLetterArgs)
                    .build();

            Binding deadLetterBinding = BindingBuilder
                    .bind(deadLetterQueue)
                    .to(deadLetterExchange)
                    .with(queueName);

            rabbitAdmin.declareQueue(deadLetterQueue);
            rabbitAdmin.declareBinding(deadLetterBinding);

            // Main queue
            Map<String, Object> args = new HashMap<>();
            args.put("x-dead-letter-exchange", deadLetterExchangeName);
            args.put("x-dead-letter-routing-key", queueName);

            Queue queue = QueueBuilder.durable(queueName)
                    .withArguments(args)
                    .build();

            rabbitAdmin.declareQueue(queue);
            queueNames.add(queueName);

            // Create bindings for each event type the subscriber is interested in
            for (Map.Entry<Class<? extends DomainEvent>, List<Class<?>>> entry : subscribers.subscribersInformation().entrySet()) {
                if (entry.getValue().contains(subscriberClass)) {
                    String routingKey = entry.getKey().getSimpleName();
                    Binding binding = BindingBuilder
                            .bind(queue)
                            .to(exchange)
                            .with(routingKey);

                    rabbitAdmin.declareBinding(binding);
                }
            }
        }

        container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueNames.toArray(new String[0]));
        container.setMessageListener(messageConsumer);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.start();
    }
}
