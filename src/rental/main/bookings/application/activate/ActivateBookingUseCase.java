package bookings.application.activate;

import bookings.domain.Booking;
import bookings.domain.BookingId;
import bookings.domain.BookingRepository;
import com.rentauto.shared.domain.bus.event.EventBus;
import org.springframework.stereotype.Service;
import vehicles.domain.Vehicle;
import vehicles.domain.VehicleRepository;

/**
 * Use case for activating a booking (vehicle pickup)
 */
@Service
public final class ActivateBookingUseCase {
    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;
    private final EventBus eventBus;

    public ActivateBookingUseCase(
            BookingRepository bookingRepository,
            VehicleRepository vehicleRepository,
            EventBus eventBus
    ) {
        this.bookingRepository = bookingRepository;
        this.vehicleRepository = vehicleRepository;
        this.eventBus = eventBus;
    }

    /**
     * Activate a booking (vehicle pickup)
     * @param id The booking ID
     * @param depositPaid Whether the deposit has been paid
     * @throws IllegalArgumentException if booking not found
     * @throws IllegalStateException if booking is not in RESERVED state or deposit is not paid
     */
    public void execute(BookingId id, boolean depositPaid) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        Vehicle vehicle = vehicleRepository.findById(booking.vehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        Booking activatedBooking = booking.activate(depositPaid);

        // Update the vehicle status to RENTED
        vehicle.rent();
        vehicleRepository.save(vehicle);

        bookingRepository.save(activatedBooking);
        eventBus.publish(activatedBooking.pullDomainEvents());
    }
}
