package bookings.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

import java.util.Arrays;
import java.util.List;

public final class BookingStatus extends StringValueObject {
    private static final List<String> VALID_STATUSES = Arrays.asList(
        "PENDING", "CONFIRMED", "CANCELLED", "COMPLETED"
    );
    
    public BookingStatus(String value) {
        super(value.toUpperCase());
        
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Booking status cannot be empty");
        }
        
        if (!VALID_STATUSES.contains(value.toUpperCase())) {
            throw new IllegalArgumentException("Invalid booking status: " + value + 
                ". Valid statuses are: " + String.join(", ", VALID_STATUSES));
        }
    }
    
    public boolean isPending() {
        return "PENDING".equals(value());
    }
    
    public boolean isConfirmed() {
        return "CONFIRMED".equals(value());
    }
    
    public boolean isCancelled() {
        return "CANCELLED".equals(value());
    }
    
    public boolean isCompleted() {
        return "COMPLETED".equals(value());
    }
}