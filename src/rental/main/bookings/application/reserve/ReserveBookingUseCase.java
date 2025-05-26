package bookings.application.reserve;

import bookings.domain.*;
import com.rentauto.shared.domain.bus.event.EventBus;
import customers.domain.Customer;
import customers.domain.CustomerId;
import customers.domain.CustomerRepository;
import vehicles.domain.Vehicle;
import vehicles.domain.VehicleId;
import vehicles.domain.VehicleRepository;

/**
 * Use case for reserving a new booking
 */
public final class ReserveBookingUseCase {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final EventBus eventBus;

    public ReserveBookingUseCase(
            BookingRepository bookingRepository,
            CustomerRepository customerRepository,
            VehicleRepository vehicleRepository,
            EventBus eventBus
    ) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
        this.eventBus = eventBus;
    }

    /**
     * Reserve a new booking
     * @param id The booking ID
     * @param dateRange The booking date range
     * @param customerId The customer ID
     * @param vehicleId The vehicle ID
     * @param price The booking price
     * @param includedMileage The included mileage
     * @return The ID of the created booking
     */
    public BookingId execute(
            BookingId id,
            BookingDateRange dateRange,
            CustomerId customerId,
            VehicleId vehicleId,
            BookingPrice price,
            BookingIncludedMileage includedMileage
    ) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customer.validateCanRent();

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        if (!vehicle.status().isAvailable()) {
            throw new IllegalArgumentException("Vehicle is not available");
        }

        // Create booking in RESERVED state
        Booking booking = Booking.reserve(
                id,
                dateRange,
                customerId,
                vehicleId,
                price,
                includedMileage
        );

        bookingRepository.save(booking);
        vehicle.reserve();
        vehicleRepository.save(vehicle);
        eventBus.publish(booking.pullDomainEvents());

        return id;
    }
}
