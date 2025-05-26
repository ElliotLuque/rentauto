package bookings.domain;

import com.rentauto.shared.domain.valueobject.IntValueObject;

/**
 * Value object representing the deposit for a booking
 */
public final class BookingDeposit extends IntValueObject {
    public BookingDeposit(int value) {
        super(value);
        
        if (value < 0) {
            throw new IllegalArgumentException("Booking deposit cannot be negative");
        }
    }
    
    public BookingDeposit add(BookingDeposit other) {
        return new BookingDeposit(this.value() + other.value());
    }
    
    public BookingDeposit subtract(BookingDeposit other) {
        int result = this.value() - other.value();
        if (result < 0) {
            throw new IllegalArgumentException("Result of deposit subtraction cannot be negative");
        }
        return new BookingDeposit(result);
    }
}