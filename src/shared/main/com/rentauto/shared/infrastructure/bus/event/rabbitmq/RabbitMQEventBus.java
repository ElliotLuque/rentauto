package com.rentauto.shared.infrastructure.bus.event.rabbitmq;

import com.rentauto.shared.domain.bus.event.DomainEvent;
import com.rentauto.shared.domain.bus.event.EventBus;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RabbitMQEventBus implements EventBus {

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;

    public RabbitMQEventBus(
            RabbitTemplate rabbitTemplate,
            @Value("${domain-event-bus.exchange}") String exchangeName
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
    }

    @Override
    public void publish(List<DomainEvent> events) {
        events.forEach(this::publishEvent);
    }

    private void publishEvent(DomainEvent event) {
        String routingKey = event.eventName();
        Map<String, Serializable> body = createMessageBody(event);

        MessageProperties properties = new MessageProperties();
        properties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        properties.setHeader("x-event-id", event.eventId());
        properties.setHeader("x-aggregate-id", event.aggregateId());
        properties.setHeader("x-occurred-on", event.occurredOn());
        properties.setHeader("x-event-name", event.eventName());

        Message message = rabbitTemplate.getMessageConverter().toMessage(body, properties);
        
        rabbitTemplate.send(exchangeName, routingKey, message);
    }

    private Map<String, Serializable> createMessageBody(DomainEvent event) {
        HashMap<String, Serializable> body = event.toPrimitives();
        
        Map<String, Serializable> message = new HashMap<>();
        message.put("data", body);
        message.put("type", event.eventName());
        message.put("occurred_on", event.occurredOn());
        message.put("id", event.eventId());
        message.put("aggregate_id", event.aggregateId());
        
        return message;
    }
}