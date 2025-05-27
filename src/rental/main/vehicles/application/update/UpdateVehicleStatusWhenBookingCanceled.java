package vehicles.application.update;

import bookings.domain.events.BookingCanceledDomainEvent;
import com.rentauto.shared.domain.bus.event.DomainEventSubscriber;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import vehicles.domain.VehicleId;
import vehicles.domain.VehicleStatus;

import java.util.UUID;

/**
 * Subscriber for BookingCanceledDomainEvent that:
 * 1. Updates the vehicle status to AVAILABLE when a booking is canceled
 */
@Service
@DomainEventSubscriber({BookingCanceledDomainEvent.class})
public final class UpdateVehicleStatusWhenBookingCanceled {
    private final UpdateVehicleStatusUseCase updateVehicleStatusUseCase;

    public UpdateVehicleStatusWhenBookingCanceled(
            UpdateVehicleStatusUseCase updateVehicleStatusUseCase
    ) {
        this.updateVehicleStatusUseCase = updateVehicleStatusUseCase;
    }

    @EventListener
    public void on(BookingCanceledDomainEvent event) {
        // Get data from the event
        String vehicleIdStr = (String) event.toPrimitives().get("vehicleId");

        VehicleId vehicleId = new VehicleId(UUID.fromString(vehicleIdStr));

        // Update vehicle status to AVAILABLE
        updateVehicleStatusUseCase.execute(vehicleId, VehicleStatus.available());
    }
}
