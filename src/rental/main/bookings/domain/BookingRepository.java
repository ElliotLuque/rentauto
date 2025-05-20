package bookings.domain;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Booking aggregate
 */
public interface BookingRepository {
    /**
     * Save a booking
     * @param booking The booking to save
     */
    void save(Booking booking);
    
    /**
     * Find a booking by its ID
     * @param id The booking ID
     * @return The booking if found
     */
    Optional<Booking> findById(BookingId id);
    
    /**
     * Find all bookings for a customer
     * @param customerId The customer ID
     * @return List of bookings
     */
    List<Booking> findByCustomerId(BookingCustomerId customerId);
    
    /**
     * Find all bookings for a vehicle
     * @param vehicleId The vehicle ID
     * @return List of bookings
     */
    List<Booking> findByVehicleId(BookingVehicleId vehicleId);
    
    /**
     * Find all bookings with a specific status
     * @param status The booking status
     * @return List of bookings
     */
    List<Booking> findByStatus(BookingStatus status);
}