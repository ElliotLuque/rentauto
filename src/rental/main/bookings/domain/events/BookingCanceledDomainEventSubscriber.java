package bookings.domain.events;

import com.rentauto.shared.domain.bus.event.DomainEventSubscriber;
import vehicles.application.update_status.UpdateVehicleStatusUseCase;
import vehicles.domain.VehicleId;
import vehicles.domain.VehicleStatus;

import java.util.UUID;

/**
 * Subscriber for BookingCanceledDomainEvent that:
 * 1. Updates the vehicle status to AVAILABLE when a booking is canceled
 */
@DomainEventSubscriber({BookingCanceledDomainEvent.class})
public final class BookingCanceledDomainEventSubscriber {
    private final UpdateVehicleStatusUseCase updateVehicleStatusUseCase;

    public BookingCanceledDomainEventSubscriber(
            UpdateVehicleStatusUseCase updateVehicleStatusUseCase
    ) {
        this.updateVehicleStatusUseCase = updateVehicleStatusUseCase;
    }

    public void on(BookingCanceledDomainEvent event) {
        // Get data from the event
        String vehicleIdStr = (String) event.toPrimitives().get("vehicleId");

        VehicleId vehicleId = new VehicleId(UUID.fromString(vehicleIdStr));

        // Update vehicle status to AVAILABLE
        updateVehicleStatusUseCase.execute(vehicleId, VehicleStatus.available());
    }
}