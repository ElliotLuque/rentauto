package bookings.application.reserve;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import bookings.domain.BookingId;
import bookings.domain.BookingDateRange;
import bookings.domain.BookingDailyRate;
import bookings.domain.BookingDeposit;
import bookings.domain.BookingIncludedMileage;
import bookings.domain.BookingPrice;
import customers.domain.CustomerId;
import vehicles.domain.VehicleId;

import java.util.UUID;

/**
 * Handler for the ReserveBookingCommand
 */
public final class ReserveBookingCommandHandler implements CommandHandler<ReserveBookingCommand> {
    private final ReserveBookingUseCase useCase;

    public ReserveBookingCommandHandler(ReserveBookingUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void handle(ReserveBookingCommand command) {
        useCase.execute(
                new BookingId(command.id()),
                new BookingDateRange(command.startDate(), command.endDate()),
                new CustomerId(UUID.fromString(command.customerId())),
                new VehicleId(UUID.fromString(command.vehicleId())),
                new BookingPrice(
                        new BookingDailyRate(command.dailyRate()),
                        new BookingDeposit(command.deposit())
                ),
                new BookingIncludedMileage(command.includedMileage())
        );
    }
}