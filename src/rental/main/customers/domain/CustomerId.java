package customers.domain;

import com.rentauto.shared.domain.valueobject.UUIDValueObject;

import java.util.UUID;

public final class CustomerId extends UUIDValueObject {
    public CustomerId(UUID value) {
        super(value);
    }

    public static CustomerId random() {
        return random(CustomerId.class);
    }
}
