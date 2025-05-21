package invoices.domain;

import com.rentauto.shared.domain.valueobject.IntValueObject;

public final class InvoiceMoney extends IntValueObject {
    public InvoiceMoney(int value) {
        super(value);
        
        if (value < 0) {
            throw new IllegalArgumentException("Invoice money cannot be negative");
        }
    }
    
    public InvoiceMoney add(InvoiceMoney other) {
        return new InvoiceMoney(this.value() + other.value());
    }
    
    public InvoiceMoney subtract(InvoiceMoney other) {
        int result = this.value() - other.value();
        if (result < 0) {
            throw new IllegalArgumentException("Result of money subtraction cannot be negative");
        }
        return new InvoiceMoney(result);
    }
    
    public InvoiceMoney multiply(int factor) {
        if (factor < 0) {
            throw new IllegalArgumentException("Multiplication factor cannot be negative");
        }
        return new InvoiceMoney(this.value() * factor);
    }
    
    public static InvoiceMoney zero() {
        return new InvoiceMoney(0);
    }
}