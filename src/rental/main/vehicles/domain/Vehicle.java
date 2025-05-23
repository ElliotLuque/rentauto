package vehicles.domain;

import com.rentauto.shared.domain.AggregateRoot;
import vehicles.domain.events.VehicleMileageUpdatedDomainEvent;
import vehicles.domain.events.VehicleStatusChangedDomainEvent;

public final class Vehicle extends AggregateRoot {
    private final VehicleId id;
    private final VehicleLicensePlate licensePlate;
    private final VehicleBrand brand;
    private final VehicleModel model;
    private final VehicleYear year;
    private final VehicleColor color;
    private final VehicleType type;
    private VehicleMileage mileage;
    private VehicleStatus status;

    public Vehicle(
            VehicleId id,
            VehicleLicensePlate licensePlate,
            VehicleBrand brand,
            VehicleModel model,
            VehicleYear year,
            VehicleColor color,
            VehicleType type,
            VehicleMileage mileage,
            VehicleStatus status
    ) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.type = type;
        this.mileage = mileage;
        this.status = status;
    }

    public static Vehicle create(
            VehicleId id,
            VehicleLicensePlate licensePlate,
            VehicleBrand brand,
            VehicleModel model,
            VehicleYear year,
            VehicleColor color,
            VehicleType type,
            VehicleMileage mileage
    ) {
        Vehicle vehicle = new Vehicle(
                id,
                licensePlate,
                brand,
                model,
                year,
                color,
                type,
                mileage,
                VehicleStatus.available()
        );

        // record(new VehicleCreatedDomainEvent(
        // ));

        return vehicle;
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

    public VehicleStatus status() {
        return status;
    }

    /**
     * Reserves a vehicle that is currently available.
     *
     * @throws IllegalStateException if the vehicle is not in AVAILABLE state
     */
    public void reserve() {
        if (this.status.canTransitionTo(VehicleStatus.reserved())) {
            status = status.transitionTo(VehicleStatus.reserved());
        }
    }

    /**
     * Marks a reserved vehicle as rented.
     *
     * @throws IllegalStateException if the vehicle is not in RESERVED state
     */
    public void rent() {
        if (this.status.canTransitionTo(VehicleStatus.rented())) {
            status = status.transitionTo(VehicleStatus.rented());
        }
    }

    /**
     * Returns a rented vehicle, making it available again.
     *
     * @throws IllegalStateException if the vehicle is not in RENTED state
     */
    public void returnVehicle() {
        if (this.status.canTransitionTo(VehicleStatus.available())) {
            status = status.transitionTo(VehicleStatus.available());
        }
    }

    /**
     * Puts an available vehicle into maintenance.
     *
     * @throws IllegalStateException if the vehicle is not in AVAILABLE state
     */
    public void startMaintenance() {
        if (this.status.canTransitionTo(VehicleStatus.onMaintenance())) {
            status = status.transitionTo(VehicleStatus.onMaintenance());
        }
    }

    /**
     * Ends maintenance for a vehicle, making it available again.
     *
     * @throws IllegalStateException if the vehicle is not in ON_MAINTENANCE state
     */
    public void endMaintenance() {
        if (this.status.canTransitionTo(VehicleStatus.available())) {
            status = status.transitionTo(VehicleStatus.available());
        }
    }

    /**
     * Updates the vehicle's mileage.
     *
     * @param newMileage The new mileage value
     */
    public void updateMileage(VehicleMileage newMileage) {
        int oldMileageValue = this.mileage.value();
        this.mileage = newMileage;

        record(new VehicleMileageUpdatedDomainEvent(
                id.value().toString(),
                oldMileageValue,
                newMileage.value()
        ));
    }

    /**
     * Changes the vehicle's status.
     *
     * @param newStatus The new status
     * @throws IllegalStateException if the status transition is not allowed
     */
    public void changeStatus(VehicleStatus newStatus) {
        VehicleStatus oldStatus = this.status;
        this.status = newStatus;

        record(new VehicleStatusChangedDomainEvent(
                id.value().toString(),
                oldStatus.value(),
                newStatus.value()
        ));
    }
}
