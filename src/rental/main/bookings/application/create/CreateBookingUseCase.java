package bookings.application.create;

import bookings.domain.*;
import customers.domain.Customer;
import customers.domain.CustomerId;
import customers.domain.CustomerRepository;
import vehicles.domain.Vehicle;
import vehicles.domain.VehicleId;
import vehicles.domain.VehicleRepository;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Use case for creating a new booking
 */
public final class CreateBookingUseCase {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    public CreateBookingUseCase(
            BookingRepository bookingRepository,
            CustomerRepository customerRepository,
            VehicleRepository vehicleRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Create a new booking
     * @param request The booking creation request
     * @return The ID of the created booking
     */
    public BookingId execute(CreateBookingRequest request) {
        // Validate customer exists
        Customer customer = customerRepository.findById(new CustomerId(UUID.fromString(request.customerId())))
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Validate customer can rent
        customer.validateCanRent();

        // Validate vehicle exists
        Vehicle vehicle = vehicleRepository.findById(new VehicleId(UUID.fromString(request.vehicleId())))
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        // Create booking ID
        BookingId bookingId = BookingId.random();

        // Create booking date range
        BookingDateRange dateRange = new BookingDateRange(
                request.startDate(),
                request.endDate()
        );

        // Calculate price (simplified for now)
        BookingPrice price = new BookingPrice(request.price().intValue());

        // Create booking with PENDING status
        Booking booking = new Booking(
                bookingId,
                dateRange,
                new BookingStatus("PENDING"),
                new CustomerId(UUID.fromString(request.customerId())),
                new VehicleId(UUID.fromString(request.vehicleId())),
                price
        );

        // Save booking
        bookingRepository.save(booking);

        return bookingId;
    }
}
