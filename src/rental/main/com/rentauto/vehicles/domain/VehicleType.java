package com.rentauto.vehicles.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

import java.util.Arrays;
import java.util.List;

public final class VehicleType extends StringValueObject {
    private static final List<String> VALID_TYPES = Arrays.asList(
        "SEDAN", "SUV", "HATCHBACK", "COUPE", "CONVERTIBLE", 
        "WAGON", "VAN", "MINIVAN", "TRUCK", "CROSSOVER"
    );
    
    public VehicleType(String value) {
        super(value.toUpperCase());
        
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Vehicle type cannot be empty");
        }
        
        if (!VALID_TYPES.contains(value.toUpperCase())) {
            throw new IllegalArgumentException("Invalid vehicle type: " + value);
        }
    }
}