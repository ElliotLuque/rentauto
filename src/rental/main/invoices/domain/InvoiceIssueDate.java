package invoices.domain;

import java.time.LocalDate;
import java.util.Objects;

public final class InvoiceIssueDate {
    private final LocalDate value;

    public InvoiceIssueDate(LocalDate value) {
        if (value == null) {
            throw new IllegalArgumentException("Issue date cannot be null");
        }
        
        this.value = value;
    }

    public LocalDate value() {
        return value;
    }

    public static InvoiceIssueDate now() {
        return new InvoiceIssueDate(LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceIssueDate that = (InvoiceIssueDate) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}