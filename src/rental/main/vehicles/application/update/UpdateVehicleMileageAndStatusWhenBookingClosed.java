package vehicles.application.update;

import bookings.domain.events.BookingClosedDomainEvent;
import com.rentauto.shared.domain.bus.event.DomainEventSubscriber;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import vehicles.domain.VehicleId;
import vehicles.domain.VehicleMileage;
import vehicles.domain.VehicleStatus;

import java.util.UUID;

/**
 * Subscriber for BookingClosedDomainEvent that:
 * 1. Updates the vehicle status to AVAILABLE
 * 2. Updates the vehicle mileage with the final mileage from the booking
 */
@Service
@DomainEventSubscriber({BookingClosedDomainEvent.class})
public final class UpdateVehicleMileageAndStatusWhenBookingClosed {
    private final UpdateVehicleStatusUseCase updateVehicleStatusUseCase;
    private final UpdateVehicleMileageUseCase updateVehicleMileageUseCase;

    public UpdateVehicleMileageAndStatusWhenBookingClosed(
            UpdateVehicleStatusUseCase updateVehicleStatusUseCase,
            UpdateVehicleMileageUseCase updateVehicleMileageUseCase
    ) {
        this.updateVehicleStatusUseCase = updateVehicleStatusUseCase;
        this.updateVehicleMileageUseCase = updateVehicleMileageUseCase;
    }

    @EventListener
    public void on(BookingClosedDomainEvent event) {
        String vehicleIdStr = (String) event.toPrimitives().get("vehicleId");
        int finalMileageValue = (int) event.toPrimitives().get("finalMileage");

        VehicleId vehicleId = new VehicleId(UUID.fromString(vehicleIdStr));

        // Update vehicle status to AVAILABLE
        updateVehicleStatusUseCase.execute(vehicleId, VehicleStatus.available());

        // Update vehicle mileage with the final mileage from the booking
        updateVehicleMileageUseCase.execute(vehicleId, new VehicleMileage(finalMileageValue));
    }
}
