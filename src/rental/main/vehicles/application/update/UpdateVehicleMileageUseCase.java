package vehicles.application.update;


import com.rentauto.shared.domain.bus.event.EventBus;
import org.springframework.stereotype.Service;
import vehicles.domain.Vehicle;
import vehicles.domain.VehicleId;
import vehicles.domain.VehicleMileage;
import vehicles.domain.VehicleRepository;

/**
 * Use case for updating a vehicle's mileage
 */
@Service
public final class UpdateVehicleMileageUseCase {
    private final VehicleRepository repository;
    private final EventBus eventBus;

    public UpdateVehicleMileageUseCase(VehicleRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    /**
     * Update a vehicle's mileage
     * @param id The vehicle ID
     * @param mileage The new mileage value
     * @throws IllegalArgumentException if the vehicle is not found or the new mileage is invalid
     */
    public void execute(VehicleId id, VehicleMileage mileage) {
        Vehicle vehicle = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found: " + id.value()));

        vehicle.updateMileage(mileage);

        repository.save(vehicle);
        eventBus.publish(vehicle.pullDomainEvents());
    }
}
