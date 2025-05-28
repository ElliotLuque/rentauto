package com.rentauto.vehicles.application.create;

import com.rentauto.shared.domain.bus.event.EventBus;
import org.springframework.stereotype.Service;
import com.rentauto.vehicles.domain.Vehicle;
import com.rentauto.vehicles.domain.VehicleBrand;
import com.rentauto.vehicles.domain.VehicleColor;
import com.rentauto.vehicles.domain.VehicleId;
import com.rentauto.vehicles.domain.VehicleLicensePlate;
import com.rentauto.vehicles.domain.VehicleMileage;
import com.rentauto.vehicles.domain.VehicleModel;
import com.rentauto.vehicles.domain.VehicleRepository;
import com.rentauto.vehicles.domain.VehicleType;
import com.rentauto.vehicles.domain.VehicleYear;

/**
 * Use case for creating a new vehicle
 */
@Service
public final class CreateVehicleUseCase {
    private final VehicleRepository repository;
    private final EventBus eventBus;

    public CreateVehicleUseCase(VehicleRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    /**
     * Create a new vehicle
     *
     * @param id           The vehicle ID
     * @param licensePlate The vehicle license plate
     * @param brand        The vehicle brand
     * @param model        The vehicle model
     * @param year         The vehicle year
     * @param color        The vehicle color
     * @param type         The vehicle type
     * @param mileage      The vehicle mileage
     * @return The ID of the created vehicle
     */
    public VehicleId execute(
            VehicleId id,
            VehicleLicensePlate licensePlate,
            VehicleBrand brand,
            VehicleModel model,
            VehicleYear year,
            VehicleColor color,
            VehicleType type,
            VehicleMileage mileage
    ) {
        // Check if license plate is already in use
        repository.findByLicensePlate(licensePlate).ifPresent(vehicle -> {
            throw new IllegalArgumentException("License plate already in use: " + licensePlate.value());
        });

        Vehicle vehicle = Vehicle.create(
                id,
                licensePlate,
                brand,
                model,
                year,
                color,
                type,
                mileage
        );

        repository.save(vehicle);
        eventBus.publish(vehicle.pullDomainEvents());

        return id;
    }
}
