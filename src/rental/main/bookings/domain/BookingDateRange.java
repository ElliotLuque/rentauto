package bookings.domain;

import java.time.LocalDateTime;

public final class BookingDateRange {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public BookingDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        validateDates();
    }

    private void validateDates() {
        if (startDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Booking start date cannot be in the past");
        }

        if (endDate.isBefore(startDate) || endDate.isEqual(startDate)) {
            throw new IllegalArgumentException("Booking end date must be after start date");
        }
    }

    public LocalDateTime startDate() {
        return startDate;
    }

    public LocalDateTime endDate() {
        return endDate;
    }
}
