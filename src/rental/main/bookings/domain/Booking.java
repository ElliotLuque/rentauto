package bookings.domain;

import bookings.domain.events.BookingActivatedDomainEvent;
import bookings.domain.events.BookingCanceledDomainEvent;
import bookings.domain.events.BookingClosedDomainEvent;
import bookings.domain.events.BookingReservedDomainEvent;
import bookings.domain.events.MileageExceededDomainEvent;
import com.rentauto.shared.domain.AggregateRoot;
import customers.domain.CustomerId;
import vehicles.domain.VehicleId;

import java.time.LocalDateTime;

/**
 * Aggregate root for the Booking entity
 * - States: RESERVED, ACTIVE, CLOSED, CANCELLED
 */
public final class Booking extends AggregateRoot {
    private final BookingId id;
    private final BookingDateRange dateRange;
    private final BookingStatus status;
    private final CustomerId customerId;
    private final VehicleId vehicleId;
    private final BookingPrice price;
    private final BookingIncludedMileage includedMileage;
    private final BookingFinalMileage finalMileage;
    private final boolean depositPaid;

    /**
     * Constructor for a new booking
     */
    public Booking(
            BookingId id,
            BookingDateRange dateRange,
            BookingStatus status,
            CustomerId customerId,
            VehicleId vehicleId,
            BookingPrice price,
            BookingIncludedMileage includedMileage,
            boolean depositPaid,
            BookingFinalMileage finalMileage
    ) {
        this.id = id;
        this.dateRange = dateRange;
        this.status = status;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.price = price;
        this.includedMileage = includedMileage;
        this.depositPaid = depositPaid;
        this.finalMileage = finalMileage;
    }

    /**
     * Factory method to create a new reserved booking
     */
    public static Booking reserve(
            BookingId id,
            BookingDateRange dateRange,
            CustomerId customerId,
            VehicleId vehicleId,
            BookingPrice price,
            BookingIncludedMileage includedMileage
    ) {
        Booking booking = new Booking(
                id,
                dateRange,
                BookingStatus.reserved(),
                customerId,
                vehicleId,
                price,
                includedMileage,
                false,
                null
        );

        booking.record(new BookingReservedDomainEvent(
                id.value().toString(),
                customerId.value().toString(),
                vehicleId.value().toString(),
                dateRange.startDate(),
                dateRange.endDate(),
                price.dailyRate().value(),
                price.deposit().value(),
                includedMileage.value()
        ));

        return booking;
    }

    /**
     * Activates a reserved booking (vehicle pickup)
     * @param depositPaid Whether the deposit has been paid
     * @throws IllegalStateException if the booking is not in RESERVED state or deposit is not paid
     */
    public Booking activate(boolean depositPaid) {
        if (!status.isReserved()) {
            throw new IllegalStateException("Booking must be in RESERVED state to activate");
        }

        if (!depositPaid) {
            throw new IllegalStateException("Deposit must be paid to activate the booking");
        }

        Booking activated = new Booking(
                id,
                dateRange,
                BookingStatus.active(),
                customerId,
                vehicleId,
                price,
                includedMileage,
                true,
                null
        );

        activated.record(new BookingActivatedDomainEvent(
                id.value().toString(),
                vehicleId.value().toString(),
                true,
                LocalDateTime.now()
        ));

        return activated;
    }

    /**
     * Closes an active booking (vehicle return)
     * @param finalMileage The final mileage of the vehicle
     * @throws IllegalStateException if the booking is not in ACTIVE state
     */
    public Booking close(BookingFinalMileage finalMileage) {
        if (!status.isActive()) {
            throw new IllegalStateException("Booking must be in ACTIVE state to close");
        }

        if (finalMileage == null) {
            throw new IllegalArgumentException("Final mileage must be provided to close the booking");
        }

        Booking closed = new Booking(
                id,
                dateRange,
                BookingStatus.closed(),
                customerId,
                vehicleId,
                price,
                includedMileage,
                depositPaid,
                finalMileage
        );

        closed.record(new BookingClosedDomainEvent(
                id.value().toString(),
                vehicleId.value().toString(),
                finalMileage.value(),
                LocalDateTime.now()
        ));

        // Check if mileage was exceeded
        int excess = finalMileage.calculateExcess(includedMileage.value());
        if (excess > 0) {
            closed.record(new MileageExceededDomainEvent(
                    id.value().toString(),
                    vehicleId.value().toString(),
                    includedMileage.value(),
                    finalMileage.value()
            ));
        }

        return closed;
    }

    /**
     * Cancels a reserved booking
     * @throws IllegalStateException if the booking is not in RESERVED state
     */
    public Booking cancel() {
        if (!status.isReserved()) {
            throw new IllegalStateException("Booking must be in RESERVED state to cancel");
        }

        Booking cancelled = new Booking(
                id,
                dateRange,
                BookingStatus.cancelled(),
                customerId,
                vehicleId,
                price,
                includedMileage,
                depositPaid,
                null
        );

        cancelled.record(new BookingCanceledDomainEvent(
                id.value().toString(),
                vehicleId.value().toString(),
                LocalDateTime.now()
        ));

        return cancelled;
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

    public BookingIncludedMileage includedMileage() {
        return includedMileage;
    }

    public BookingDeposit deposit() {
        return price.deposit();
    }

    public BookingDailyRate dailyRate() {
        return price.dailyRate();
    }

    public boolean isDepositPaid() {
        return depositPaid;
    }

    public BookingFinalMileage finalMileage() {
        return finalMileage;
    }
}
