package com.rentauto.bookings.application.close;

import com.rentauto.shared.domain.bus.command.Command;

import java.util.UUID;

/**
 * Command for closing a booking (vehicle return)
 */
public record CloseBookingCommand(
        UUID id,
        int finalMileage
) implements Command {}