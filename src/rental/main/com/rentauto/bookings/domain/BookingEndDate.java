package com.rentauto.bookings.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class BookingEndDate extends StringValueObject {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    
    public BookingEndDate(String value) {
        super(value);
        
        try {
            LocalDate.parse(value, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use ISO format (YYYY-MM-DD): " + value);
        }
    }
    
    public void ensureEndDateIsAfterStartDate(BookingStartDate startDate) {
        LocalDate start = startDate.toLocalDate();
        LocalDate end = this.toLocalDate();
        
        if (end.isBefore(start) || end.isEqual(start)) {
            throw new IllegalArgumentException("Booking end date must be after start date");
        }
    }
    
    public LocalDate toLocalDate() {
        return LocalDate.parse(value(), FORMATTER);
    }
}