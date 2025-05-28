package com.rentauto.bookings.application.find;

import com.rentauto.shared.domain.bus.query.Response;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Response for the FindBookingByIdQuery
 */
public record FindBookingByIdResponse(
        Optional<BookingResponse> booking
) implements Response {

    public record BookingResponse(
            UUID id,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String status,
            UUID customerId,
            UUID vehicleId,
            int dailyRate,
            int deposit,
            int includedMileage
    ) {}
}