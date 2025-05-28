package com.rentauto.invoices.domain;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class InvoiceCanceledDomainEvent extends DomainEvent {
    private final String reason;
    private final boolean generatesRectification;

    public InvoiceCanceledDomainEvent(
            String aggregateId,
            String reason,
            boolean generatesRectification
    ) {
        super(aggregateId);
        this.reason = reason;
        this.generatesRectification = generatesRectification;
    }

    public InvoiceCanceledDomainEvent(
            String aggregateId,
            String reason,
            boolean generatesRectification,
            String eventId,
            String occurredOn
    ) {
        super(aggregateId, eventId, occurredOn);
        this.reason = reason;
        this.generatesRectification = generatesRectification;
    }

    @Override
    public String eventName() {
        return "invoice.canceled";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> primitives = new HashMap<>();
        primitives.put("reason", reason);
        primitives.put("generatesRectification", generatesRectification);
        return primitives;
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new InvoiceCanceledDomainEvent(
                aggregateId,
                (String) body.get("reason"),
                (boolean) body.get("generatesRectification"),
                eventId,
                occurredOn
        );
    }

    public String reason() {
        return reason;
    }

    public boolean generatesRectification() {
        return generatesRectification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceCanceledDomainEvent that = (InvoiceCanceledDomainEvent) o;
        return generatesRectification == that.generatesRectification &&
               Objects.equals(aggregateId(), that.aggregateId()) &&
               Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId(), reason, generatesRectification);
    }
}