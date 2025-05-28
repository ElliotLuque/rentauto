package com.rentauto.bookings.application.close;

import com.rentauto.bookings.domain.Booking;
import com.rentauto.bookings.domain.BookingFinalMileage;
import com.rentauto.bookings.domain.BookingId;
import com.rentauto.bookings.domain.BookingRepository;
import com.rentauto.shared.domain.bus.event.EventBus;
import com.rentauto.vehicles.domain.Vehicle;
import com.rentauto.vehicles.domain.VehicleMileage;
import com.rentauto.vehicles.domain.VehicleRepository;

/**
 * Use case for closing a booking (vehicle return)
 */
public final class CloseBookingUseCase {
    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;
    private final EventBus eventBus;

    public CloseBookingUseCase(
            BookingRepository bookingRepository,
            VehicleRepository vehicleRepository,
            EventBus eventBus
    ) {
        this.bookingRepository = bookingRepository;
        this.vehicleRepository = vehicleRepository;
        this.eventBus = eventBus;
    }

    /**
     * Close a booking (vehicle return)
     * @param id The booking ID
     * @param finalMileage The final mileage of the vehicle
     * @throws IllegalArgumentException if booking not found
     * @throws IllegalStateException if booking is not in ACTIVE state
     */
    public void execute(BookingId id, BookingFinalMileage finalMileage) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        Vehicle vehicle = vehicleRepository.findById(booking.vehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        Booking closedBooking = booking.close(finalMileage);

        // Update the vehicle mileage and status
        vehicle.updateMileage(new VehicleMileage(finalMileage.value()));
        vehicle.returnVehicle();
        vehicleRepository.save(vehicle);

        bookingRepository.save(closedBooking);
        eventBus.publish(closedBooking.pullDomainEvents());
    }
}