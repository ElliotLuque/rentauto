package com.rentauto.vehicles.domain;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Vehicle aggregate
 */
public interface VehicleRepository {
    /**
     * Save a vehicle
     * @param vehicle The vehicle to save
     */
    void save(Vehicle vehicle);
    
    /**
     * Find a vehicle by ID
     * @param id The vehicle ID
     * @return The vehicle if found
     */
    Optional<Vehicle> findById(VehicleId id);
    
    /**
     * Find a vehicle by license plate
     * @param licensePlate The vehicle license plate
     * @return The vehicle if found
     */
    Optional<Vehicle> findByLicensePlate(VehicleLicensePlate licensePlate);
    
    /**
     * Find vehicles by brand
     * @param brand The vehicle brand
     * @return List of matching vehicles
     */
    List<Vehicle> findByBrand(VehicleBrand brand);
    
    /**
     * Find vehicles by type
     * @param type The vehicle type
     * @return List of matching vehicles
     */
    List<Vehicle> findByType(VehicleType type);
    
    /**
     * Find all available vehicles (not currently booked)
     * @return List of available vehicles
     */
    List<Vehicle> findAvailable();
    
    /**
     * Find all vehicles
     * @return List of all vehicles
     */
    List<Vehicle> findAll();
}