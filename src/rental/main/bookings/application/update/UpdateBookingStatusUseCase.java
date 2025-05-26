package bookings.application.update;

import bookings.domain.Booking;
import bookings.domain.BookingId;
import bookings.domain.BookingRepository;
import bookings.domain.BookingStatus;
import com.rentauto.shared.domain.bus.event.EventBus;

/**
 * Use case for updating a booking's status
 */
public final class UpdateBookingStatusUseCase {
    private final BookingRepository repository;
    private final EventBus eventBus;

    public UpdateBookingStatusUseCase(BookingRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    /**
     * Update a booking's status
     * @param bookingId The ID of the booking to update
     * @param newStatus The new status for the booking
     * @throws IllegalArgumentException if booking not found
     */
    public void execute(BookingId bookingId, BookingStatus newStatus) {
        // Find the booking
        Booking booking = repository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        // Create a new booking with the updated status
        // Note: Since Booking is immutable, we need to create a new instance
        // TODO: CREATE BOOKING

        // Save the updated booking
        //repository.save(updatedBooking);

        // Publish domain events
        //eventBus.publish(updatedBooking.pullDomainEvents());
    }
}
