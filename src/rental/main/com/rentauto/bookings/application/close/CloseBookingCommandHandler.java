package com.rentauto.bookings.application.close;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import com.rentauto.bookings.domain.BookingId;
import com.rentauto.bookings.domain.BookingFinalMileage;

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