package customers.domain;

import com.rentauto.shared.domain.valueobject.UUIDValueObject;

import java.util.UUID;

public final class CustomerId extends UUIDValueObject {
    public CustomerId(UUID value) {
        super(value);
    }
    
    public static CustomerId create(UUID value) {
        return new CustomerId(value);
    }
    
    public static CustomerId random() {
        return new CustomerId(UUID.randomUUID());
    }
}