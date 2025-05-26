package bookings.application.activate;

import com.rentauto.shared.domain.bus.command.Command;

import java.util.UUID;

/**
 * Command for activating a booking (vehicle pickup)
 */
public record ActivateBookingCommand(
        UUID id,
        boolean depositPaid
) implements Command {}