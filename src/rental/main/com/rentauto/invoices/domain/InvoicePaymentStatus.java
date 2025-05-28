package com.rentauto.invoices.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

import java.util.Arrays;
import java.util.List;

public final class InvoicePaymentStatus extends StringValueObject {
    private static final List<String> VALID_STATUSES = Arrays.asList(
        "PENDING", "PAID", "CANCELED"
    );
    
    public InvoicePaymentStatus(String value) {
        super(value.toUpperCase());
        
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Payment status cannot be empty");
        }
        
        if (!VALID_STATUSES.contains(value.toUpperCase())) {
            throw new IllegalArgumentException("Invalid payment status: " + value + 
                ". Valid statuses are: " + String.join(", ", VALID_STATUSES));
        }
    }
    
    public boolean isPending() {
        return "PENDING".equals(value());
    }
    
    public boolean isPaid() {
        return "PAID".equals(value());
    }
    
    public boolean isCanceled() {
        return "CANCELED".equals(value());
    }
    
    public static InvoicePaymentStatus pending() {
        return new InvoicePaymentStatus("PENDING");
    }
    
    public static InvoicePaymentStatus paid() {
        return new InvoicePaymentStatus("PAID");
    }
    
    public static InvoicePaymentStatus canceled() {
        return new InvoicePaymentStatus("CANCELED");
    }
}