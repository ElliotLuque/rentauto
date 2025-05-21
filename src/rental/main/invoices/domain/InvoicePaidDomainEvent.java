package invoices.domain;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class InvoicePaidDomainEvent extends DomainEvent {
    private final int amountPaid;

    public InvoicePaidDomainEvent(
            String aggregateId,
            int amountPaid
    ) {
        super(aggregateId);
        this.amountPaid = amountPaid;
    }

    public InvoicePaidDomainEvent(
            String aggregateId,
            int amountPaid,
            String eventId,
            String occurredOn
    ) {
        super(aggregateId, eventId, occurredOn);
        this.amountPaid = amountPaid;
    }

    @Override
    public String eventName() {
        return "invoice.paid";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> primitives = new HashMap<>();
        primitives.put("amountPaid", amountPaid);
        return primitives;
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new InvoicePaidDomainEvent(
                aggregateId,
                (int) body.get("amountPaid"),
                eventId,
                occurredOn
        );
    }

    public int amountPaid() {
        return amountPaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoicePaidDomainEvent that = (InvoicePaidDomainEvent) o;
        return amountPaid == that.amountPaid &&
               Objects.equals(aggregateId(), that.aggregateId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId(), amountPaid);
    }
}