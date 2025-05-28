package com.rentauto.invoices.domain;

import com.rentauto.shared.domain.valueobject.UUIDValueObject;

import java.util.UUID;

public final class InvoiceId extends UUIDValueObject {
    public InvoiceId(UUID value) {
        super(value);
    }

    public static InvoiceId random() {
        return random(InvoiceId.class);
    }
}
