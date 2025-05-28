package com.rentauto.bookings.application.reserve;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import com.rentauto.bookings.domain.BookingId;
import com.rentauto.bookings.domain.BookingDateRange;
import com.rentauto.bookings.domain.BookingDailyRate;
import com.rentauto.bookings.domain.BookingDeposit;
import com.rentauto.bookings.domain.BookingIncludedMileage;
import com.rentauto.bookings.domain.BookingPrice;
import com.rentauto.customers.domain.CustomerId;
import com.rentauto.vehicles.domain.VehicleId;

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