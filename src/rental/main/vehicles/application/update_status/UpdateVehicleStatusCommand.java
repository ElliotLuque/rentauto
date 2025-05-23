package vehicles.application.update_status;

import com.rentauto.shared.domain.bus.command.Command;

import java.util.UUID;

/**
 * Command for updating a vehicle's status
 */
public record UpdateVehicleStatusCommand(UUID id, String status) implements Command {
}