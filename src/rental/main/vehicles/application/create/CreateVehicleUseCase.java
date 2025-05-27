package vehicles.application.create;

import com.rentauto.shared.domain.bus.event.EventBus;
import org.springframework.stereotype.Service;
import vehicles.domain.Vehicle;
import vehicles.domain.VehicleBrand;
import vehicles.domain.VehicleColor;
import vehicles.domain.VehicleId;
import vehicles.domain.VehicleLicensePlate;
import vehicles.domain.VehicleMileage;
import vehicles.domain.VehicleModel;
import vehicles.domain.VehicleRepository;
import vehicles.domain.VehicleType;
import vehicles.domain.VehicleYear;

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
