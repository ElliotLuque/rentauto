package bookings.application.close;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import bookings.domain.BookingId;
import bookings.domain.BookingFinalMileage;

/**
 * Handler for the CloseBookingCommand
 */
public final class CloseBookingCommandHandler implements CommandHandler<CloseBookingCommand> {
    private final CloseBookingUseCase useCase;

    public CloseBookingCommandHandler(CloseBookingUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void handle(CloseBookingCommand command) {
        useCase.execute(
                new BookingId(command.id()),
                new BookingFinalMileage(command.finalMileage())
        );
    }
}