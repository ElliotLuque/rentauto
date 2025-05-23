package vehicles.domain.events;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class VehicleCreatedDomainEvent extends DomainEvent {
    private final String licensePlate;
    private final String brand;
    private final String model;
    private final int year;
    private final String color;
    private final String type;
    private final int mileage;
    private final String status;

    public VehicleCreatedDomainEvent(
            String aggregateId,
            String licensePlate,
            String brand,
            String model,
            int year,
            String color,
            String type,
            int mileage,
            String status
    ) {
        super(aggregateId);
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.type = type;
        this.mileage = mileage;
        this.status = status;
    }

    public VehicleCreatedDomainEvent(
            String aggregateId,
            String licensePlate,
            String brand,
            String model,
            int year,
            String color,
            String type,
            int mileage,
            String status,
            String eventId,
            String occurredOn
    ) {
        super(aggregateId, eventId, occurredOn);
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.type = type;
        this.mileage = mileage;
        this.status = status;
    }

    @Override
    public String eventName() {
        return "vehicle.created";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> primitives = new HashMap<>();
        primitives.put("licensePlate", licensePlate);
        primitives.put("brand", brand);
        primitives.put("model", model);
        primitives.put("year", year);
        primitives.put("color", color);
        primitives.put("type", type);
        primitives.put("mileage", mileage);
        primitives.put("status", status);
        return primitives;
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new VehicleCreatedDomainEvent(
                aggregateId,
                (String) body.get("licensePlate"),
                (String) body.get("brand"),
                (String) body.get("model"),
                (Integer) body.get("year"),
                (String) body.get("color"),
                (String) body.get("type"),
                (Integer) body.get("mileage"),
                (String) body.get("status"),
                eventId,
                occurredOn
        );
    }

    public String licensePlate() {
        return licensePlate;
    }

    public String brand() {
        return brand;
    }

    public String model() {
        return model;
    }

    public int year() {
        return year;
    }

    public String color() {
        return color;
    }

    public String type() {
        return type;
    }

    public int mileage() {
        return mileage;
    }

    public String status() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleCreatedDomainEvent that = (VehicleCreatedDomainEvent) o;
        return year == that.year &&
               mileage == that.mileage &&
               Objects.equals(aggregateId(), that.aggregateId()) &&
               Objects.equals(licensePlate, that.licensePlate) &&
               Objects.equals(brand, that.brand) &&
               Objects.equals(model, that.model) &&
               Objects.equals(color, that.color) &&
               Objects.equals(type, that.type) &&
               Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId(), licensePlate, brand, model, year, color, type, mileage, status);
    }
}