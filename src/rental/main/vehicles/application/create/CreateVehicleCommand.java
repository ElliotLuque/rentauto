package vehicles.application.create;

import com.rentauto.shared.domain.bus.command.Command;

import java.util.UUID;

/**
 * Command for creating a vehicle
 */
public record CreateVehicleCommand(
        UUID id,
        String licensePlate,
        String brand,
        String model,
        int year,
        String color,
        String type,
        int mileage
) implements Command {};