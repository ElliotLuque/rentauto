package vehicles.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

import java.util.Map;
import java.util.Set;

/**
 * Represents the possible states of a vehicle in the rental system.
 * Follows these state transition rules:
 * - AVAILABLE → RESERVED → RENTED → AVAILABLE
 * - AVAILABLE ↔ ON_MAINTENANCE
 * - RENTED cannot transition to ON_MAINTENANCE without first returning to AVAILABLE
 */
public final class VehicleStatus extends StringValueObject {
    public static final String AVAILABLE = "AVAILABLE";
    public static final String RESERVED = "RESERVED";
    public static final String RENTED = "RENTED";
    public static final String ON_MAINTENANCE = "ON_MAINTENANCE";

    private static final Set<String> VALID_STATUSES = Set.of(
            AVAILABLE, RESERVED, RENTED, ON_MAINTENANCE
    );

    private static final Map<String, Set<String>> ALLOWED_TRANSITIONS = Map.of(
            AVAILABLE,      Set.of(RESERVED, ON_MAINTENANCE),
            RESERVED,       Set.of(RENTED),
            RENTED,         Set.of(AVAILABLE),
            ON_MAINTENANCE, Set.of(AVAILABLE)
    );

    public static VehicleStatus available()      { return new VehicleStatus(AVAILABLE); }
    public static VehicleStatus reserved()       { return new VehicleStatus(RESERVED); }
    public static VehicleStatus rented()         { return new VehicleStatus(RENTED); }
    public static VehicleStatus onMaintenance()  { return new VehicleStatus(ON_MAINTENANCE); }

    public boolean isAvailable()      { return value().equals(AVAILABLE); }
    public boolean isReserved()       { return value().equals(RESERVED); }
    public boolean isRented()         { return value().equals(RENTED); }
    public boolean isOnMaintenance()  { return value().equals(ON_MAINTENANCE); }

    public VehicleStatus(String value) {
        super(value);
        ensureIsValid(value);
    }

    public boolean canTransitionTo(VehicleStatus target) {
        return ALLOWED_TRANSITIONS
                .getOrDefault(this.value(), Set.of())
                .contains(target.value());
    }

    public VehicleStatus transitionTo(VehicleStatus target) {
        if (!canTransitionTo(target)) {
            throw new IllegalStateException(
                    "Transition not permitted: " + this.value() + " → " + target.value()
            );
        }
        return target;
    }

    private void ensureIsValid(String value) {
        if (!VALID_STATUSES.contains(value)) {
            throw new IllegalArgumentException(
                    "Vehicle status not valid: " + value
            );
        }
    }
}