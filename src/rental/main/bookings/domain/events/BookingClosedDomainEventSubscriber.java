package bookings.domain.events;

import com.rentauto.shared.domain.bus.event.DomainEventSubscriber;
import vehicles.application.update_mileage.UpdateVehicleMileageUseCase;
import vehicles.application.update_status.UpdateVehicleStatusUseCase;
import vehicles.domain.VehicleId;
import vehicles.domain.VehicleMileage;
import vehicles.domain.VehicleStatus;

import java.util.UUID;

/**
 * Subscriber for BookingClosedDomainEvent that:
 * 1. Updates the vehicle status to AVAILABLE
 * 2. Updates the vehicle mileage with the final mileage from the booking
 */
@DomainEventSubscriber({BookingClosedDomainEvent.class})
public final class BookingClosedDomainEventSubscriber {
    private final UpdateVehicleStatusUseCase updateVehicleStatusUseCase;
    private final UpdateVehicleMileageUseCase updateVehicleMileageUseCase;

    public BookingClosedDomainEventSubscriber(
            UpdateVehicleStatusUseCase updateVehicleStatusUseCase,
            UpdateVehicleMileageUseCase updateVehicleMileageUseCase
    ) {
        this.updateVehicleStatusUseCase = updateVehicleStatusUseCase;
        this.updateVehicleMileageUseCase = updateVehicleMileageUseCase;
    }

    public void on(BookingClosedDomainEvent event) {
        // Get data from the event
        String vehicleIdStr = (String) event.toPrimitives().get("vehicleId");
        int finalMileageValue = (int) event.toPrimitives().get("finalMileage");

        VehicleId vehicleId = new VehicleId(UUID.fromString(vehicleIdStr));

        // Update vehicle status to AVAILABLE
        updateVehicleStatusUseCase.execute(vehicleId, VehicleStatus.available());

        // Update vehicle mileage with the final mileage from the booking
        updateVehicleMileageUseCase.execute(vehicleId, new VehicleMileage(finalMileageValue));
    }
}
