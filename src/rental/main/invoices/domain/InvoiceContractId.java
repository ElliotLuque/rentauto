package invoices.domain;

import com.rentauto.shared.domain.valueobject.UUIDValueObject;

import java.util.UUID;

public final class InvoiceContractId extends UUIDValueObject {
    public InvoiceContractId(UUID value) {
        super(value);
    }

    public static InvoiceContractId random() {
        return random(InvoiceContractId.class);
    }
}
