package vehicles.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

import java.util.regex.Pattern;

public final class VehicleLicensePlate extends StringValueObject {
    private final Pattern spanishPlateRegex = Pattern.compile("^[0-9]{4}[A-Z]{3}$");

    public VehicleLicensePlate(String value) {
        super(value);

        if (!spanishPlateRegex.matcher(value).matches())
            throw new IllegalArgumentException("Invalid license plate format: " + value);
    }
}
