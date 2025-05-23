package vehicles.application.update_mileage;

import com.rentauto.shared.domain.bus.command.Command;

import java.util.UUID;

/**
 * Command for updating a vehicle's mileage
 */
public record UpdateVehicleMileageCommand(UUID id, int mileage) implements Command {
}