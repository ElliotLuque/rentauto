package bookings.domain.events;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Domain event for when a booking is reserved
 */
public final class BookingReservedDomainEvent extends DomainEvent {
    private final String customerId;
    private final String vehicleId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final int dailyRate;
    private final int deposit;
    private final int includedMileage;

    public BookingReservedDomainEvent(
            String aggregateId,
            String customerId,
            String vehicleId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int dailyRate,
            int deposit,
            int includedMileage
    ) {
        super(aggregateId);
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dailyRate = dailyRate;
        this.deposit = deposit;
        this.includedMileage = includedMileage;
    }

    public BookingReservedDomainEvent(
            String aggregateId,
            String eventId,
            String occurredOn,
            String customerId,
            String vehicleId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int dailyRate,
            int deposit,
            int includedMileage
    ) {
        super(aggregateId, eventId, occurredOn);
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dailyRate = dailyRate;
        this.deposit = deposit;
        this.includedMileage = includedMileage;
    }

    @Override
    public String eventName() {
        return "booking.reserved";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<>() {{
            put("customerId", customerId);
            put("vehicleId", vehicleId);
            put("startDate", startDate.toString());
            put("endDate", endDate.toString());
            put("dailyRate", dailyRate);
            put("deposit", deposit);
            put("includedMileage", includedMileage);
        }};
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new BookingReservedDomainEvent(
                aggregateId,
                eventId,
                occurredOn,
                (String) body.get("customerId"),
                (String) body.get("vehicleId"),
                LocalDateTime.parse((String) body.get("startDate")),
                LocalDateTime.parse((String) body.get("endDate")),
                (int) body.get("dailyRate"),
                (int) body.get("deposit"),
                (int) body.get("includedMileage")
        );
    }
}