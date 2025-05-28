package com.rentauto.bookings.domain;

import com.rentauto.shared.domain.valueobject.IntValueObject;

/**
 * Value object representing the daily rate for a booking
 */
public final class BookingDailyRate extends IntValueObject {
    public BookingDailyRate(int value) {
        super(value);
        
        if (value < 0) {
            throw new IllegalArgumentException("Booking daily rate cannot be negative");
        }
    }
    
    public BookingDailyRate add(BookingDailyRate other) {
        return new BookingDailyRate(this.value() + other.value());
    }
    
    public BookingDailyRate multiply(int factor) {
        if (factor < 0) {
            throw new IllegalArgumentException("Multiplication factor cannot be negative");
        }
        return new BookingDailyRate(this.value() * factor);
    }
}