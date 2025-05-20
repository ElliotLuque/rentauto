package bookings.application.cancel;

import bookings.application.update.UpdateBookingStatusRequest;
import bookings.application.update.UpdateBookingStatusUseCase;
import bookings.domain.Booking;
import bookings.domain.BookingId;
import bookings.domain.BookingRepository;

import java.util.UUID;

/**
 * Use case for canceling a booking
 */
public final class CancelBookingUseCase {
    private final BookingRepository repository;
    private final UpdateBookingStatusUseCase updateStatusUseCase;

    public CancelBookingUseCase(
            BookingRepository repository,
            UpdateBookingStatusUseCase updateStatusUseCase
    ) {
        this.repository = repository;
        this.updateStatusUseCase = updateStatusUseCase;
    }

    /**
     * Cancel a booking
     * @param bookingId The ID of the booking to cancel
     * @throws IllegalArgumentException if booking not found or already canceled
     */
    public void execute(String bookingId) {
        // Find the booking
        BookingId id = new BookingId(UUID.fromString(bookingId));
        Booking booking = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        
        // Check if booking is already canceled
        if (booking.status().isCancelled()) {
            throw new IllegalArgumentException("Booking is already cancelled");
        }
        
        // Check if booking is completed
        if (booking.status().isCompleted()) {
            throw new IllegalArgumentException("Cannot cancel a completed booking");
        }
        
        // Update booking status to CANCELLED
        updateStatusUseCase.execute(new UpdateBookingStatusRequest(bookingId, "CANCELLED"));
    }
}