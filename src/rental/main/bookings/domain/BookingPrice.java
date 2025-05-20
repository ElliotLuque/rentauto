package bookings.domain;

import com.rentauto.shared.domain.valueobject.IntValueObject;

public final class BookingPrice extends IntValueObject {
    public BookingPrice(int value) {
        super(value);
        
        if (value < 0) {
            throw new IllegalArgumentException("Booking price cannot be negative");
        }
    }
    
    public BookingPrice add(BookingPrice other) {
        return new BookingPrice(this.value() + other.value());
    }
    
    public BookingPrice subtract(BookingPrice other) {
        int result = this.value() - other.value();
        if (result < 0) {
            throw new IllegalArgumentException("Result of price subtraction cannot be negative");
        }
        return new BookingPrice(result);
    }
    
    public BookingPrice multiply(int factor) {
        if (factor < 0) {
            throw new IllegalArgumentException("Multiplication factor cannot be negative");
        }
        return new BookingPrice(this.value() * factor);
    }
}