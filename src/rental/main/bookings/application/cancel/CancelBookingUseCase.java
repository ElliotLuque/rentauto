package bookings.application.cancel;

import bookings.domain.Booking;
import bookings.domain.BookingId;
import bookings.domain.BookingRepository;
import com.rentauto.shared.domain.bus.event.EventBus;
import org.springframework.stereotype.Service;
import vehicles.domain.Vehicle;
import vehicles.domain.VehicleRepository;

/**
 * Use case for canceling a booking
 */
@Service
public final class CancelBookingUseCase {
    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;
    private final EventBus eventBus;

    public CancelBookingUseCase(
            BookingRepository bookingRepository,
            VehicleRepository vehicleRepository,
            EventBus eventBus
    ) {
        this.bookingRepository = bookingRepository;
        this.vehicleRepository = vehicleRepository;
        this.eventBus = eventBus;
    }

    /**
     * Cancel a booking
     * @param id The booking ID
     * @throws IllegalArgumentException if booking not found
     * @throws IllegalStateException if booking is not in RESERVED state
     */
    public void execute(BookingId id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        Vehicle vehicle = vehicleRepository.findById(booking.vehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        Booking canceledBooking = booking.cancel();

        // Make the vehicle available again
        vehicle.returnVehicle();
        vehicleRepository.save(vehicle);

        bookingRepository.save(canceledBooking);
        eventBus.publish(canceledBooking.pullDomainEvents());
    }
}
