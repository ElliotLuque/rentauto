package com.rentauto.bookings.domain;

import com.rentauto.shared.domain.valueobject.UUIDValueObject;

import java.util.UUID;

public final class BookingVehicleId extends UUIDValueObject {
    public BookingVehicleId(UUID value) {
        super(value);
    }
}