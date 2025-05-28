package com.rentauto.bookings.domain.events;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Domain event for when a booking is closed (vehicle return)
 */
public final class BookingClosedDomainEvent extends DomainEvent {
    private final String vehicleId;
    private final int finalMileage;
    private final LocalDateTime closingDate;

    public BookingClosedDomainEvent(
            String aggregateId,
            String vehicleId,
            int finalMileage,
            LocalDateTime closingDate
    ) {
        super(aggregateId);
        this.vehicleId = vehicleId;
        this.finalMileage = finalMileage;
        this.closingDate = closingDate;
    }

    public BookingClosedDomainEvent(
            String aggregateId,
            String eventId,
            String occurredOn,
            String vehicleId,
            int finalMileage,
            LocalDateTime closingDate
    ) {
        super(aggregateId, eventId, occurredOn);
        this.vehicleId = vehicleId;
        this.finalMileage = finalMileage;
        this.closingDate = closingDate;
    }

    @Override
    public String eventName() {
        return "booking.closed";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<>() {{
            put("vehicleId", vehicleId);
            put("finalMileage", finalMileage);
            put("closingDate", closingDate.toString());
        }};
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new BookingClosedDomainEvent(
                aggregateId,
                eventId,
                occurredOn,
                (String) body.get("vehicleId"),
                (int) body.get("finalMileage"),
                LocalDateTime.parse((String) body.get("closingDate"))
        );
    }
}