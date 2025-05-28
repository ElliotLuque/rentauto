package com.rentauto.customers.domain;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class CustomerRentalHistoryIncrementedDomainEvent extends DomainEvent {
    private final int rentalCount;

    public CustomerRentalHistoryIncrementedDomainEvent(
            String aggregateId,
            int rentalCount
    ) {
        super(aggregateId);
        this.rentalCount = rentalCount;
    }

    public CustomerRentalHistoryIncrementedDomainEvent(
            String aggregateId,
            int rentalCount,
            String eventId,
            String occurredOn
    ) {
        super(aggregateId, eventId, occurredOn);
        this.rentalCount = rentalCount;
    }

    @Override
    public String eventName() {
        return "customer.rental_history.incremented";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> primitives = new HashMap<>();
        primitives.put("rentalCount", rentalCount);
        return primitives;
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new CustomerRentalHistoryIncrementedDomainEvent(
                aggregateId,
                (int) body.get("rentalCount"),
                eventId,
                occurredOn
        );
    }

    public int rentalCount() {
        return rentalCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerRentalHistoryIncrementedDomainEvent that = (CustomerRentalHistoryIncrementedDomainEvent) o;
        return rentalCount == that.rentalCount &&
               Objects.equals(aggregateId(), that.aggregateId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId(), rentalCount);
    }
}