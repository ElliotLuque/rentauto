package com.rentauto.vehicles.domain;

import com.rentauto.shared.domain.valueobject.IntValueObject;

import java.time.Year;

public final class VehicleYear extends IntValueObject {
    public VehicleYear(int value) {
        super(value);
        
        int currentYear = Year.now().getValue();
        if (value < 1900 || value > currentYear) {
            throw new IllegalArgumentException("Vehicle year must be between 1900 and " + currentYear);
        }
    }
}