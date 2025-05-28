package com.rentauto.customers.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

import java.util.regex.Pattern;

public final class CustomerPhone extends StringValueObject {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,15}$");
    
    public CustomerPhone(String value) {
        super(value);
        ensureValidPhone(value);
    }
    
    private void ensureValidPhone(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        
        if (!PHONE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid phone number format. Must be 10-15 digits, optionally starting with +");
        }
    }
}