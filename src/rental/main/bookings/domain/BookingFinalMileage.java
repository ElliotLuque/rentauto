package bookings.domain;

import com.rentauto.shared.domain.valueobject.IntValueObject;

/**
 * Value object representing the final mileage for a booking
 */
public final class BookingFinalMileage extends IntValueObject {
    public BookingFinalMileage(int value) {
        super(value);
        
        if (value < 0) {
            throw new IllegalArgumentException("Booking final mileage cannot be negative");
        }
    }
    
    public boolean isGreaterThan(int other) {
        return this.value() > other;
    }
    
    public int calculateExcess(int includedMileage) {
        // If includedMileage is 0, it means unlimited mileage, so no excess
        if (includedMileage == 0) {
            return 0;
        }
        return Math.max(0, this.value() - includedMileage);
    }
}