package vehicles.domain.events;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class VehicleStatusChangedDomainEvent extends DomainEvent {
    private final String oldStatus;
    private final String newStatus;

    public VehicleStatusChangedDomainEvent(
            String aggregateId,
            String oldStatus,
            String newStatus
    ) {
        super(aggregateId);
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public VehicleStatusChangedDomainEvent(
            String aggregateId,
            String oldStatus,
            String newStatus,
            String eventId,
            String occurredOn
    ) {
        super(aggregateId, eventId, occurredOn);
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    @Override
    public String eventName() {
        return "vehicle.status.changed";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> primitives = new HashMap<>();
        primitives.put("oldStatus", oldStatus);
        primitives.put("newStatus", newStatus);
        return primitives;
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new VehicleStatusChangedDomainEvent(
                aggregateId,
                (String) body.get("oldStatus"),
                (String) body.get("newStatus"),
                eventId,
                occurredOn
        );
    }

    public String oldStatus() {
        return oldStatus;
    }

    public String newStatus() {
        return newStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleStatusChangedDomainEvent that = (VehicleStatusChangedDomainEvent) o;
        return Objects.equals(aggregateId(), that.aggregateId()) &&
               Objects.equals(oldStatus, that.oldStatus) &&
               Objects.equals(newStatus, that.newStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId(), oldStatus, newStatus);
    }
}