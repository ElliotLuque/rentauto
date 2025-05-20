package bookings.application.create;

import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * Request object for creating a booking
 */
public final class CreateBookingRequest {
    private final String customerId;
    private final String vehicleId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final BigDecimal price;

    public CreateBookingRequest(
            String customerId,
            String vehicleId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            BigDecimal price
    ) {
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public String customerId() {
        return customerId;
    }

    public String vehicleId() {
        return vehicleId;
    }

    public LocalDateTime startDate() {
        return startDate;
    }

    public LocalDateTime endDate() {
        return endDate;
    }

    public BigDecimal price() {
        return price;
    }
}