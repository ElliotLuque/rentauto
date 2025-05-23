package vehicles.application.update_status;


import com.rentauto.shared.domain.bus.event.EventBus;
import vehicles.domain.Vehicle;
import vehicles.domain.VehicleId;
import vehicles.domain.VehicleRepository;
import vehicles.domain.VehicleStatus;

/**
 * Use case for updating a vehicle's status
 */
public final class UpdateVehicleStatusUseCase {
    private final VehicleRepository repository;
    private final EventBus eventBus;

    public UpdateVehicleStatusUseCase(VehicleRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    /**
     * Update a vehicle's status
     * @param id The vehicle ID
     * @param status The new status value
     * @throws IllegalArgumentException if the vehicle is not found or the status is invalid
     * @throws IllegalStateException if the status transition is not allowed
     */
    public void execute(VehicleId id, VehicleStatus status) {
        Vehicle vehicle = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found: " + id.value()));

        vehicle.changeStatus(status);

        repository.save(vehicle);
        eventBus.publish(vehicle.pullDomainEvents());
    }
}
