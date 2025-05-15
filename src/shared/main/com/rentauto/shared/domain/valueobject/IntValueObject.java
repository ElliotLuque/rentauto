package com.rentauto.shared.domain.valueobject;

import java.util.Objects;

public abstract class IntValueObject {
    private final int value;

    public IntValueObject(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntValueObject that)) return false;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
