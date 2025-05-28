package com.rentauto.invoices.domain;

import java.util.Objects;

public final class InvoiceLine {
    private final String description;
    private final InvoiceMoney amount;

    public InvoiceLine(String description, InvoiceMoney amount) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        
        this.description = description;
        this.amount = amount;
    }

    public String description() {
        return description;
    }

    public InvoiceMoney amount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceLine that = (InvoiceLine) o;
        return Objects.equals(description, that.description) &&
               Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, amount);
    }
}