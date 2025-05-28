package com.rentauto.vehicles.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

public final class VehicleBrand extends StringValueObject {
    public VehicleBrand(String value) {
        super(value);
        
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Vehicle brand cannot be empty");
        }
    }
}