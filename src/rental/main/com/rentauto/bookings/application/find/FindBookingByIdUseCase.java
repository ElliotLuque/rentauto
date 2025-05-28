package com.rentauto.bookings.application.find;

import com.rentauto.bookings.domain.Booking;
import com.rentauto.bookings.domain.BookingId;
import com.rentauto.bookings.domain.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Use case for finding a booking by its ID
 */
@Service
public final class FindBookingByIdUseCase {
    private final BookingRepository repository;

    public FindBookingByIdUseCase(BookingRepository repository) {
        this.repository = repository;
    }

    /**
     * Find a booking by its ID
     * @param id The booking ID
     * @return The booking if found
     */
    public Optional<Booking> execute(BookingId id) {
        return repository.findById(id);
    }
}
