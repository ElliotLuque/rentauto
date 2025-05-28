package com.rentauto.bookings.domain;

import com.rentauto.shared.domain.AggregateRoot;

/**
 * Value object representing the price for a booking
 * Includes daily rate and deposit
 */
public final class BookingPrice extends AggregateRoot {
    private final BookingDailyRate dailyRate;
    private final BookingDeposit deposit;

    public BookingPrice(BookingDailyRate dailyRate, BookingDeposit deposit) {
        this.dailyRate = dailyRate;
        this.deposit = deposit;
    }

    public BookingDailyRate dailyRate() {
        return dailyRate;
    }

    public BookingDeposit deposit() {
        return deposit;
    }

    public int calculateTotalPrice(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Days must be positive");
        }
        return dailyRate.value() * days;
    }

    public int calculateTotalWithDeposit(int days) {
        return calculateTotalPrice(days) + deposit.value();
    }
}
