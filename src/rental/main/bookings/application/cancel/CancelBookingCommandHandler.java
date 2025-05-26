package bookings.application.cancel;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import bookings.domain.BookingId;

/**
 * Handler for the CancelBookingCommand
 */
public final class CancelBookingCommandHandler implements CommandHandler<CancelBookingCommand> {
    private final CancelBookingUseCase useCase;

    public CancelBookingCommandHandler(CancelBookingUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void handle(CancelBookingCommand command) {
        useCase.execute(new BookingId(command.id()));
    }
}