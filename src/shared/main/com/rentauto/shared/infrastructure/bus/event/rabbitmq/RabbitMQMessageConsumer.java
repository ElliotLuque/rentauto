package com.rentauto.shared.infrastructure.bus.event.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentauto.shared.domain.bus.event.DomainEvent;
import com.rentauto.shared.infrastructure.bus.event.DomainEventSubscribersInformation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Service
public final class RabbitMQMessageConsumer implements MessageListener {
    private final DomainEventSubscribersInformation information;
    private final ApplicationContext context;
    private final ObjectMapper objectMapper;
    private final HashMap<String, Class<? extends DomainEvent>> eventTypes = new HashMap<>();

    public RabbitMQMessageConsumer(
            DomainEventSubscribersInformation information,
            ApplicationContext context,
            ObjectMapper objectMapper
    ) {
        this.information = information;
        this.context = context;
        this.objectMapper = objectMapper;
        this.loadEventTypes();
    }

    @Override
    public void onMessage(Message message) {
        try {
            String eventName = message.getMessageProperties().getHeader("x-event-name");
            String aggregateId = message.getMessageProperties().getHeader("x-aggregate-id");
            String eventId = message.getMessageProperties().getHeader("x-event-id");
            String occurredOn = message.getMessageProperties().getHeader("x-occurred-on");

            Class<? extends DomainEvent> eventClass = eventTypes.get(eventName);
            if (eventClass == null) {
                return;
            }

            Map<String, Object> body = objectMapper.readValue(message.getBody(), Map.class);
            Map<String, Object> attributes = (Map<String, Object>) body.get("data");

            DomainEvent event = eventClass.getDeclaredConstructor().newInstance();
            Method fromPrimitivesMethod = eventClass.getMethod(
                    "fromPrimitives",
                    String.class,
                    HashMap.class,
                    String.class,
                    String.class
            );

            event = (DomainEvent) fromPrimitivesMethod.invoke(
                    event,
                    aggregateId,
                    new HashMap<>(attributes),
                    eventId,
                    occurredOn
            );

            final DomainEvent finalEvent = event;
            information.subscribersFor(eventClass).forEach(subscriberClass -> {
                try {
                    Object subscriber = context.getBean(subscriberClass);
                    Method subscriberOnMethod = subscriberClass.getMethod("on", eventClass);
                    subscriberOnMethod.invoke(subscriber, finalEvent);
                } catch (Exception e) {
                    throw new RuntimeException("Error processing event " + eventName, e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Error processing message", e);
        }
    }

    private void loadEventTypes() {
        information.subscribersInformation().keySet().forEach(eventClass -> {
            try {
                DomainEvent event = eventClass.getDeclaredConstructor().newInstance();
                eventTypes.put(event.eventName(), eventClass);
            } catch (Exception e) {
                throw new RuntimeException("Error loading event types", e);
            }
        });
    }
}
