package customers.domain;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class CustomerCreatedDomainEvent extends DomainEvent {
    private final String name;
    private final String email;
    private final String phone;

    public CustomerCreatedDomainEvent(
            String aggregateId,
            String name,
            String email,
            String phone
    ) {
        super(aggregateId);
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public CustomerCreatedDomainEvent(
            String aggregateId,
            String name,
            String email,
            String phone,
            String eventId,
            String occurredOn
    ) {
        super(aggregateId, eventId, occurredOn);
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String eventName() {
        return "customer.created";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> primitives = new HashMap<>();
        primitives.put("name", name);
        primitives.put("email", email);
        primitives.put("phone", phone);
        return primitives;
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new CustomerCreatedDomainEvent(
                aggregateId,
                (String) body.get("name"),
                (String) body.get("email"),
                (String) body.get("phone"),
                eventId,
                occurredOn
        );
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public String phone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerCreatedDomainEvent that = (CustomerCreatedDomainEvent) o;
        return Objects.equals(aggregateId(), that.aggregateId()) &&
               Objects.equals(name, that.name) &&
               Objects.equals(email, that.email) &&
               Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId(), name, email, phone);
    }
}