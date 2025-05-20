package vehicles.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

public final class VehicleColor extends StringValueObject {
    public VehicleColor(String value) {
        super(value);
        
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Vehicle color cannot be empty");
        }
    }
}