package com.rentauto.vehicles.domain;

import com.rentauto.shared.domain.valueobject.IntValueObject;

public final class VehicleMileage extends IntValueObject {
    public VehicleMileage(int value) {
        super(value);
        
        if (value < 0) {
            throw new IllegalArgumentException("Vehicle mileage cannot be negative");
        }
        
        // Most vehicles don't exceed 1,000,000 miles in their lifetime
        if (value > 1_000_000) {
            throw new IllegalArgumentException("Vehicle mileage exceeds reasonable limit");
        }
    }
}