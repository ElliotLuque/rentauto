package com.rentauto.shared.infrastructure.bus.event;

import com.rentauto.shared.domain.bus.event.DomainEvent;
import com.rentauto.shared.domain.bus.event.DomainEventSubscriber;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public final class DomainEventSubscribersInformation {
    private final Map<Class<? extends DomainEvent>, List<Class<?>>> information;

    public DomainEventSubscribersInformation() {
        this.information = scanDomainEventSubscribers();
    }

    public Collection<Class<?>> all() {
        return information.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public Map<Class<? extends DomainEvent>, List<Class<?>>> subscribersInformation() {
        return information;
    }

    public List<Class<?>> subscribersFor(Class<? extends DomainEvent> eventClass) {
        return information.getOrDefault(eventClass, Collections.emptyList());
    }

    private Map<Class<? extends DomainEvent>, List<Class<?>>> scanDomainEventSubscribers() {
        Reflections reflections = new Reflections("com.rentauto");
        Set<Class<?>> subscribers = reflections.getTypesAnnotatedWith(DomainEventSubscriber.class);

        Map<Class<? extends DomainEvent>, List<Class<?>>> subscribersInformation = new HashMap<>();

        for (Class<?> subscriberClass : subscribers) {
            DomainEventSubscriber annotation = subscriberClass.getAnnotation(DomainEventSubscriber.class);

            for (Class<? extends DomainEvent> eventClass : annotation.value()) {
                List<Class<?>> subscribersForEvent = subscribersInformation.getOrDefault(eventClass, new ArrayList<>());
                subscribersForEvent.add(subscriberClass);
                subscribersInformation.put(eventClass, subscribersForEvent);
            }
        }

        return subscribersInformation;
    }

    public String queueNameFor(Class<?> subscriberClass) {
        return subscriberClass.getSimpleName();
    }

    public String formatQueueName(String queueName) {
        return queueName
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();
    }
}