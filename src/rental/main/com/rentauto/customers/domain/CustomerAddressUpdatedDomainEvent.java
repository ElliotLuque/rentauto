package com.rentauto.customers.domain;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class CustomerAddressUpdatedDomainEvent extends DomainEvent {
    private final String address;

    public CustomerAddressUpdatedDomainEvent(
            String aggregateId,
            String address
    ) {
        super(aggregateId);
        this.address = address;
    }

    public CustomerAddressUpdatedDomainEvent(
            String aggregateId,
            String address,
            String eventId,
            String occurredOn
    ) {
        super(aggregateId, eventId, occurredOn);
        this.address = address;
    }

    @Override
    public String eventName() {
        return "customer.address.updated";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> primitives = new HashMap<>();
        primitives.put("address", address);
        return primitives;
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new CustomerAddressUpdatedDomainEvent(
                aggregateId,
                (String) body.get("address"),
                eventId,
                occurredOn
        );
    }

    public String address() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerAddressUpdatedDomainEvent that = (CustomerAddressUpdatedDomainEvent) o;
        return Objects.equals(aggregateId(), that.aggregateId()) &&
               Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId(), address);
    }
}