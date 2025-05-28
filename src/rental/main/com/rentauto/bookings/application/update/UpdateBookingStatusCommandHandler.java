package com.rentauto.bookings.application.update;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import com.rentauto.bookings.domain.BookingId;
import com.rentauto.bookings.domain.BookingStatus;

/**
 * Handler for the UpdateBookingStatusCommand
 */
public final class UpdateBookingStatusCommandHandler implements CommandHandler<UpdateBookingStatusCommand> {
    private final UpdateBookingStatusUseCase useCase;

    public UpdateBookingStatusCommandHandler(UpdateBookingStatusUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void handle(UpdateBookingStatusCommand command) {
        useCase.execute(
                new BookingId(command.id()),
                new BookingStatus(command.newStatus())
        );
    }
}