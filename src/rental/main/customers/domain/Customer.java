package customers.domain;

import com.rentauto.shared.domain.AggregateRoot;

import java.util.Objects;
import java.util.UUID;

public final class Customer extends AggregateRoot {
    private final CustomerId id;
    private final CustomerName name;
    private final CustomerEmail email;
    private final CustomerPhone phone;
    private CustomerAddress address;
    private CustomerRentalHistory rentalHistory;

    public Customer(
            CustomerId id,
            CustomerName name,
            CustomerEmail email,
            CustomerPhone phone,
            CustomerAddress address,
            CustomerRentalHistory rentalHistory
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.rentalHistory = rentalHistory;
    }

    public static Customer create(
            CustomerId id,
            CustomerName name,
            CustomerEmail email,
            CustomerPhone phone
    ) {
        return create(id, name, email, phone, null, new CustomerRentalHistory(0));
    }

    public static Customer create(
            CustomerId id,
            CustomerName name,
            CustomerEmail email,
            CustomerPhone phone,
            CustomerAddress address
    ) {
        return create(id, name, email, phone, address, new CustomerRentalHistory(0));
    }

    public static Customer create(
            CustomerId id,
            CustomerName name,
            CustomerEmail email,
            CustomerPhone phone,
            CustomerAddress address,
            CustomerRentalHistory rentalHistory
    ) {
        Customer customer = new Customer(id, name, email, phone, address, rentalHistory);

        customer.record(new CustomerCreatedDomainEvent(
                id.value().toString(),
                name.value(),
                email.value(),
                phone.value()
        ));

        return customer;
    }

    public static Customer create(
            String name,
            String email,
            String phone
    ) {
        return create(
                CustomerId.random(),
                new CustomerName(name),
                new CustomerEmail(email),
                new CustomerPhone(phone),
                null,
                new CustomerRentalHistory(0)
        );
    }

    public void validateCanRent() {
        // In a real application, this might check customer status, payment history, etc.
        // For now, we'll just assume all customers can rent
    }

    public CustomerId id() {
        return id;
    }

    public CustomerName name() {
        return name;
    }

    public CustomerEmail email() {
        return email;
    }

    public CustomerPhone phone() {
        return phone;
    }

    public CustomerAddress address() {
        return address;
    }

    public CustomerRentalHistory rentalHistory() {
        return rentalHistory;
    }

    public boolean isEligibleForDiscount() {
        return rentalHistory != null && rentalHistory.isEligibleForDiscount();
    }

    public void incrementRentalHistory() {
        if (rentalHistory == null) {
            rentalHistory = new CustomerRentalHistory(1);
        } else {
            rentalHistory = rentalHistory.increment();
        }

        record(new CustomerRentalHistoryIncrementedDomainEvent(
                id.value().toString(),
                rentalHistory.value()
        ));
    }

    public void updateAddress(CustomerAddress newAddress) {
        this.address = newAddress;

        record(new CustomerAddressUpdatedDomainEvent(
                id.value().toString(),
                newAddress.value()
        ));
    }

    public void updateAddress(String newAddress) {
        updateAddress(new CustomerAddress(newAddress));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
