package com.rentauto.bookings.domain;

import com.rentauto.shared.domain.valueobject.UUIDValueObject;

import java.util.UUID;

public final class BookingCustomerId extends UUIDValueObject {
    public BookingCustomerId(UUID value) {
        super(value);
    }
}