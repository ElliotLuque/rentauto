package bookings.application.find;

import bookings.domain.Booking;
import bookings.domain.BookingId;
import com.rentauto.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Handler for the FindBookingByIdQuery
 */
@Service
public final class FindBookingByIdQueryHandler implements QueryHandler<FindBookingByIdQuery, FindBookingByIdResponse> {
    private final FindBookingByIdUseCase useCase;

    public FindBookingByIdQueryHandler(FindBookingByIdUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public FindBookingByIdResponse handle(FindBookingByIdQuery query) {
        Optional<Booking> booking = useCase.execute(new BookingId(query.id()));

        return new FindBookingByIdResponse(
                booking.map(b -> new FindBookingByIdResponse.BookingResponse(
                        b.id().value(),
                        b.startDate(),
                        b.endDate(),
                        b.status().value(),
                        b.customerId().value(),
                        b.vehicleId().value(),
                        b.price().dailyRate().value(),
                        b.price().deposit().value(),
                        b.includedMileage().value()
                ))
        );
    }
}
