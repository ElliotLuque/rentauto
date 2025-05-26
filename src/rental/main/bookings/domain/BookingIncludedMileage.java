package bookings.domain;

import com.rentauto.shared.domain.valueobject.IntValueObject;

/**
 * Value object representing the included mileage for a booking
 */
public final class BookingIncludedMileage extends IntValueObject {
    public BookingIncludedMileage(int value) {
        super(value);
        
        if (value < 0) {
            throw new IllegalArgumentException("Booking included mileage cannot be negative");
        }
    }
    
    public BookingIncludedMileage add(BookingIncludedMileage other) {
        return new BookingIncludedMileage(this.value() + other.value());
    }
    
    public boolean isUnlimited() {
        return this.value() == 0;
    }
    
    public static BookingIncludedMileage unlimited() {
        return new BookingIncludedMileage(0);
    }
}