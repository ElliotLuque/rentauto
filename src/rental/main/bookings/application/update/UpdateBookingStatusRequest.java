package bookings.application.update;

/**
 * Request object for updating a booking's status
 */
public final class UpdateBookingStatusRequest {
    private final String bookingId;
    private final String newStatus;

    public UpdateBookingStatusRequest(String bookingId, String newStatus) {
        this.bookingId = bookingId;
        this.newStatus = newStatus;
    }

    public String bookingId() {
        return bookingId;
    }

    public String newStatus() {
        return newStatus;
    }
}