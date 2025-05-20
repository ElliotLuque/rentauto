package com.rentauto.shared.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public abstract class UUIDValueObject {
    private final UUID value;

    public UUIDValueObject(UUID value) {
        this.value = value;
    }

    public UUID value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UUIDValueObject that)) return false;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
