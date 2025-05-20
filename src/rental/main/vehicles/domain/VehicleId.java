package vehicles.domain;

import com.rentauto.shared.domain.valueobject.UUIDValueObject;

import java.util.UUID;

public final class VehicleId extends UUIDValueObject {
    public VehicleId(UUID value) {
        super(value);
    }
}
