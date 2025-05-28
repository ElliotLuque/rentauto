package com.rentauto.customers.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

import java.util.regex.Pattern;

public final class CustomerEmail extends StringValueObject {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    
    public CustomerEmail(String value) {
        super(value);
        ensureValidEmail(value);
    }
    
    private void ensureValidEmail(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}