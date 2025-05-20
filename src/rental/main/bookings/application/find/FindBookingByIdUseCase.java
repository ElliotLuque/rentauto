package bookings.application.find;

import bookings.domain.Booking;
import bookings.domain.BookingId;
import bookings.domain.BookingRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Use case for finding a booking by its ID
 */
public final class FindBookingByIdUseCase {
    private final BookingRepository repository;

    public FindBookingByIdUseCase(BookingRepository repository) {
        this.repository = repository;
    }

    /**
     * Find a booking by its ID
     * @param id The booking ID as a string
     * @return The booking if found
     */
    public Optional<Booking> execute(String id) {
        BookingId bookingId = new BookingId(UUID.fromString(id));
        return repository.findById(bookingId);
    }
}