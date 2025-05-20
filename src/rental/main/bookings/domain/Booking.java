package bookings.domain;

import com.rentauto.shared.domain.AggregateRoot;
import customers.domain.CustomerId;
import vehicles.domain.VehicleId;

import java.time.LocalDateTime;

public final class Booking extends AggregateRoot {
    private final BookingId id;
    private final BookingDateRange dateRange;
    private final BookingStatus status;
    private final CustomerId customerId;
    private final VehicleId vehicleId;
    private final BookingPrice price;

    public Booking(
            BookingId id,
            BookingDateRange dateRange,
            BookingStatus status,
            CustomerId customerId,
            VehicleId vehicleId,
            BookingPrice price
    ) {
        this.id = id;
        this.dateRange = dateRange;
        this.status = status;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.price = price;
    }

    public BookingId id() {
        return id;
    }

    public LocalDateTime startDate() {
        return dateRange.startDate();
    }

    public LocalDateTime endDate() {
        return dateRange.endDate();
    }

    public BookingDateRange dateRange() {
        return dateRange;
    }

    public BookingStatus status() {
        return status;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public VehicleId vehicleId() {
        return vehicleId;
    }

    public BookingPrice price() {
        return price;
    }
}
