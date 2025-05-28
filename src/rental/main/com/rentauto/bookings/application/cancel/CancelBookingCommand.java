package com.rentauto.bookings.application.cancel;

import com.rentauto.shared.domain.bus.command.Command;

import java.util.UUID;

/**
 * Command for canceling a booking
 */
public record CancelBookingCommand(
        UUID id
) implements Command {}