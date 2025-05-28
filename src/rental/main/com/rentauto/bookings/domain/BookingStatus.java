package com.rentauto.bookings.domain;

import com.rentauto.shared.domain.valueobject.StringValueObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents the possible states of a booking in the rental system.
 * Follows these state transition rules:
 * - RESERVED → ACTIVE → CLOSED
 * - RESERVED → CANCELLED
 */
public final class BookingStatus extends StringValueObject {
    public static final String RESERVED = "RESERVED";
    public static final String ACTIVE = "ACTIVE";
    public static final String CLOSED = "CLOSED";
    public static final String CANCELLED = "CANCELLED";

    private static final List<String> VALID_STATUSES = Arrays.asList(
        RESERVED, ACTIVE, CLOSED, CANCELLED
    );

    private static final Map<String, Set<String>> ALLOWED_TRANSITIONS = Map.of(
        RESERVED, Set.of(ACTIVE, CANCELLED),
        ACTIVE, Set.of(CLOSED),
        CLOSED, Set.of(),
        CANCELLED, Set.of()
    );

    public static BookingStatus reserved() { return new BookingStatus(RESERVED); }
    public static BookingStatus active() { return new BookingStatus(ACTIVE); }
    public static BookingStatus closed() { return new BookingStatus(CLOSED); }
    public static BookingStatus cancelled() { return new BookingStatus(CANCELLED); }

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

    public boolean isReserved() {
        return RESERVED.equals(value());
    }

    public boolean isActive() {
        return ACTIVE.equals(value());
    }

    public boolean isClosed() {
        return CLOSED.equals(value());
    }

    public boolean isCancelled() {
        return CANCELLED.equals(value());
    }

    public boolean canTransitionTo(BookingStatus target) {
        return ALLOWED_TRANSITIONS
                .getOrDefault(this.value(), Set.of())
                .contains(target.value());
    }

    public BookingStatus transitionTo(BookingStatus target) {
        if (!canTransitionTo(target)) {
            throw new IllegalStateException(
                    "Transition not permitted: " + this.value() + " → " + target.value()
            );
        }
        return target;
    }
}
