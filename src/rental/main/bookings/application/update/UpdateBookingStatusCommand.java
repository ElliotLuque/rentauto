package bookings.application.update;

import com.rentauto.shared.domain.bus.command.Command;

import java.util.UUID;

/**
 * Command for updating a booking's status
 */
public record UpdateBookingStatusCommand(
        UUID id,
        String newStatus
) implements Command {}