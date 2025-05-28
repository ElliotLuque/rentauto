package com.rentauto.customers.domain;

import com.rentauto.shared.domain.valueobject.IntValueObject;

public final class CustomerRentalHistory extends IntValueObject {
    public CustomerRentalHistory(int value) {
        super(value);
        ensureValidRentalHistory(value);
    }
    
    private void ensureValidRentalHistory(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Rental history count cannot be negative");
        }
    }
    
    public boolean isEligibleForDiscount() {
        return value() >= 3;
    }
    
    public CustomerRentalHistory increment() {
        return new CustomerRentalHistory(value() + 1);
    }
}