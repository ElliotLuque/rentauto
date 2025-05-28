package com.rentauto.customers.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

public final class CustomerName extends StringValueObject {
    public CustomerName(String value) {
        super(value);
        ensureValidName(value);
    }
    
    private void ensureValidName(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        
        if (value.length() > 100) {
            throw new IllegalArgumentException("Customer name cannot be longer than 100 characters");
        }
    }
}