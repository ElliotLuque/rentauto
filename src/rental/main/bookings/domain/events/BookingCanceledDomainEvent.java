package bookings.domain.events;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Domain event for when a booking is canceled
 */
public final class BookingCanceledDomainEvent extends DomainEvent {
    private final String vehicleId;
    private final LocalDateTime cancellationDate;

    public BookingCanceledDomainEvent(
            String aggregateId,
            String vehicleId,
            LocalDateTime cancellationDate
    ) {
        super(aggregateId);
        this.vehicleId = vehicleId;
        this.cancellationDate = cancellationDate;
    }

    public BookingCanceledDomainEvent(
            String aggregateId,
            String eventId,
            String occurredOn,
            String vehicleId,
            LocalDateTime cancellationDate
    ) {
        super(aggregateId, eventId, occurredOn);
        this.vehicleId = vehicleId;
        this.cancellationDate = cancellationDate;
    }

    @Override
    public String eventName() {
        return "booking.canceled";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<>() {{
            put("vehicleId", vehicleId);
            put("cancellationDate", cancellationDate.toString());
        }};
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new BookingCanceledDomainEvent(
                aggregateId,
                eventId,
                occurredOn,
                (String) body.get("vehicleId"),
                LocalDateTime.parse((String) body.get("cancellationDate"))
        );
    }
}