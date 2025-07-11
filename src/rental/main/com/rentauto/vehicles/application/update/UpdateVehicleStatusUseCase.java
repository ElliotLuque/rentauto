package com.rentauto.vehicles.application.update;


import com.rentauto.shared.domain.bus.event.EventBus;
import org.springframework.stereotype.Service;
import com.rentauto.vehicles.domain.Vehicle;
import com.rentauto.vehicles.domain.VehicleId;
import com.rentauto.vehicles.domain.VehicleRepository;
import com.rentauto.vehicles.domain.VehicleStatus;

/**
 * Use case for updating a vehicle's status
 */
@Service
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
