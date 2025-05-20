package bookings.application.update;

import bookings.domain.Booking;
import bookings.domain.BookingId;
import bookings.domain.BookingRepository;
import bookings.domain.BookingStatus;

import java.util.UUID;

/**
 * Use case for updating a booking's status
 */
public final class UpdateBookingStatusUseCase {
    private final BookingRepository repository;

    public UpdateBookingStatusUseCase(BookingRepository repository) {
        this.repository = repository;
    }

    /**
     * Update a booking's status
     * @param request The update request containing booking ID and new status
     * @throws IllegalArgumentException if booking not found
     */
    public void execute(UpdateBookingStatusRequest request) {
        // Find the booking
        BookingId bookingId = new BookingId(UUID.fromString(request.bookingId()));
        Booking booking = repository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        
        // Create a new booking with the updated status
        // Note: Since Booking is immutable, we need to create a new instance
        Booking updatedBooking = new Booking(
                booking.id(),
                booking.dateRange(),
                new BookingStatus(request.newStatus()),
                booking.customerId(),
                booking.vehicleId(),
                booking.price()
        );
        
        // Save the updated booking
        repository.save(updatedBooking);
    }
}