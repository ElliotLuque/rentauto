package com.rentauto.bookings.domain.events;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Domain event for when a booking is activated (vehicle pickup)
 */
public final class BookingActivatedDomainEvent extends DomainEvent {
    private final String vehicleId;
    private final boolean depositPaid;
    private final LocalDateTime activationDate;

    public BookingActivatedDomainEvent(
            String aggregateId,
            String vehicleId,
            boolean depositPaid,
            LocalDateTime activationDate
    ) {
        super(aggregateId);
        this.vehicleId = vehicleId;
        this.depositPaid = depositPaid;
        this.activationDate = activationDate;
    }

    public BookingActivatedDomainEvent(
            String aggregateId,
            String eventId,
            String occurredOn,
            String vehicleId,
            boolean depositPaid,
            LocalDateTime activationDate
    ) {
        super(aggregateId, eventId, occurredOn);
        this.vehicleId = vehicleId;
        this.depositPaid = depositPaid;
        this.activationDate = activationDate;
    }

    @Override
    public String eventName() {
        return "booking.activated";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<>() {{
            put("vehicleId", vehicleId);
            put("depositPaid", depositPaid);
            put("activationDate", activationDate.toString());
        }};
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new BookingActivatedDomainEvent(
                aggregateId,
                eventId,
                occurredOn,
                (String) body.get("vehicleId"),
                (boolean) body.get("depositPaid"),
                LocalDateTime.parse((String) body.get("activationDate"))
        );
    }
}