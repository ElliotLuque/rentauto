package vehicles.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

public final class VehicleModel extends StringValueObject {
    public VehicleModel(String value) {
        super(value);
        
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Vehicle model cannot be empty");
        }
    }
}