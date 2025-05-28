package com.rentauto.bookings.application.find;

import com.rentauto.shared.domain.bus.query.Query;

import java.util.UUID;

/**
 * Query for finding a booking by its ID
 */
public record FindBookingByIdQuery(
        UUID id
) implements Query {}