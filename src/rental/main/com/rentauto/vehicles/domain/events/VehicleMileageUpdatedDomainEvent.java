package com.rentauto.vehicles.domain.events;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class VehicleMileageUpdatedDomainEvent extends DomainEvent {
    private final int oldMileage;
    private final int newMileage;

    public VehicleMileageUpdatedDomainEvent(
            String aggregateId,
            int oldMileage,
            int newMileage
    ) {
        super(aggregateId);
        this.oldMileage = oldMileage;
        this.newMileage = newMileage;
    }

    public VehicleMileageUpdatedDomainEvent(
            String aggregateId,
            int oldMileage,
            int newMileage,
            String eventId,
            String occurredOn
    ) {
        super(aggregateId, eventId, occurredOn);
        this.oldMileage = oldMileage;
        this.newMileage = newMileage;
    }

    @Override
    public String eventName() {
        return "vehicle.mileage.updated";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> primitives = new HashMap<>();
        primitives.put("oldMileage", oldMileage);
        primitives.put("newMileage", newMileage);
        return primitives;
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new VehicleMileageUpdatedDomainEvent(
                aggregateId,
                (Integer) body.get("oldMileage"),
                (Integer) body.get("newMileage"),
                eventId,
                occurredOn
        );
    }

    public int oldMileage() {
        return oldMileage;
    }

    public int newMileage() {
        return newMileage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleMileageUpdatedDomainEvent that = (VehicleMileageUpdatedDomainEvent) o;
        return oldMileage == that.oldMileage &&
               newMileage == that.newMileage &&
               Objects.equals(aggregateId(), that.aggregateId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId(), oldMileage, newMileage);
    }
}