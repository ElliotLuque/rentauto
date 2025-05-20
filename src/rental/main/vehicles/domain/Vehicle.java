package vehicles.domain;

import com.rentauto.shared.domain.AggregateRoot;

public final class Vehicle extends AggregateRoot {
    private final VehicleId id;
    private final VehicleLicensePlate licensePlate;
    private final VehicleBrand brand;
    private final VehicleModel model;
    private final VehicleYear year;
    private final VehicleColor color;
    private final VehicleType type;
    private final VehicleMileage mileage;

    public Vehicle(
            VehicleId id, 
            VehicleLicensePlate licensePlate,
            VehicleBrand brand,
            VehicleModel model,
            VehicleYear year,
            VehicleColor color,
            VehicleType type,
            VehicleMileage mileage
    ) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.type = type;
        this.mileage = mileage;
    }

    public VehicleId id() {
        return id;
    }

    public VehicleLicensePlate licensePlate() {
        return licensePlate;
    }

    public VehicleBrand brand() {
        return brand;
    }

    public VehicleModel model() {
        return model;
    }

    public VehicleYear year() {
        return year;
    }

    public VehicleColor color() {
        return color;
    }

    public VehicleType type() {
        return type;
    }

    public VehicleMileage mileage() {
        return mileage;
    }
}
