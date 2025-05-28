package com.rentauto.customers.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

public final class CustomerAddress extends StringValueObject {
    public CustomerAddress(String value) {
        super(value);
        ensureValidAddress(value);
    }
    
    private void ensureValidAddress(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        
        if (value.length() > 255) {
            throw new IllegalArgumentException("Address cannot be longer than 255 characters");
        }
    }
}