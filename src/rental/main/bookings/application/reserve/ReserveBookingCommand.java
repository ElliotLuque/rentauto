package bookings.application.reserve;

import com.rentauto.shared.domain.bus.command.Command;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Command for reserving a booking
 */
public record ReserveBookingCommand(
        UUID id,
        String customerId,
        String vehicleId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int dailyRate,
        int deposit,
        int includedMileage
) implements Command {}