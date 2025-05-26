package bookings.domain.events;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Domain event for when the included mileage is exceeded
 */
public final class MileageExceededDomainEvent extends DomainEvent {
    private final String vehicleId;
    private final int includedMileage;
    private final int actualMileage;
    private final int excessMileage;

    public MileageExceededDomainEvent(
            String aggregateId,
            String vehicleId,
            int includedMileage,
            int actualMileage
    ) {
        super(aggregateId);
        this.vehicleId = vehicleId;
        this.includedMileage = includedMileage;
        this.actualMileage = actualMileage;
        this.excessMileage = calculateExcessMileage(includedMileage, actualMileage);
    }

    public MileageExceededDomainEvent(
            String aggregateId,
            String eventId,
            String occurredOn,
            String vehicleId,
            int includedMileage,
            int actualMileage,
            int excessMileage
    ) {
        super(aggregateId, eventId, occurredOn);
        this.vehicleId = vehicleId;
        this.includedMileage = includedMileage;
        this.actualMileage = actualMileage;
        this.excessMileage = excessMileage;
    }

    private int calculateExcessMileage(int includedMileage, int actualMileage) {
        // If includedMileage is 0, it means unlimited mileage, so no excess
        if (includedMileage == 0) {
            return 0;
        }
        return Math.max(0, actualMileage - includedMileage);
    }

    @Override
    public String eventName() {
        return "booking.mileage_exceeded";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<>() {{
            put("vehicleId", vehicleId);
            put("includedMileage", includedMileage);
            put("actualMileage", actualMileage);
            put("excessMileage", excessMileage);
        }};
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new MileageExceededDomainEvent(
                aggregateId,
                eventId,
                occurredOn,
                (String) body.get("vehicleId"),
                (int) body.get("includedMileage"),
                (int) body.get("actualMileage"),
                (int) body.get("excessMileage")
        );
    }
}
