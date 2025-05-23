package bookings.domain;

import com.rentauto.shared.domain.valueobject.UUIDValueObject;

import java.util.UUID;

public final class BookingId extends UUIDValueObject {
    public BookingId(UUID value) { 
        super(value);
    }

    public static BookingId random() {
        return random(BookingId.class);
    }
}
