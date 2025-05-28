package com.rentauto.bookings.application.activate;

import com.rentauto.bookings.domain.BookingId;
import com.rentauto.shared.domain.bus.command.CommandHandler;

/**
 * Handler for the ActivateBookingCommand
 */
public final class ActivateBookingCommandHandler implements CommandHandler<ActivateBookingCommand> {
    private final ActivateBookingUseCase useCase;

    public ActivateBookingCommandHandler(ActivateBookingUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void handle(ActivateBookingCommand command) {
        useCase.execute(
                new BookingId(command.id()),
                command.depositPaid()
        );
    }
}