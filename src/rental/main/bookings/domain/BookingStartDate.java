package bookings.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class BookingStartDate extends StringValueObject {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    
    public BookingStartDate(String value) {
        super(value);
        
        try {
            LocalDate date = LocalDate.parse(value, FORMATTER);
            
            if (date.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Booking start date cannot be in the past");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use ISO format (YYYY-MM-DD): " + value);
        }
    }
    
    public LocalDate toLocalDate() {
        return LocalDate.parse(value(), FORMATTER);
    }
}