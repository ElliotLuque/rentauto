package vehicles.application.create;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;
import vehicles.domain.VehicleBrand;
import vehicles.domain.VehicleColor;
import vehicles.domain.VehicleId;
import vehicles.domain.VehicleLicensePlate;
import vehicles.domain.VehicleMileage;
import vehicles.domain.VehicleModel;
import vehicles.domain.VehicleType;
import vehicles.domain.VehicleYear;

@Service
public final class CreateVehicleCommandHandler implements CommandHandler<CreateVehicleCommand> {
    private final CreateVehicleUseCase useCase;

    public CreateVehicleCommandHandler(CreateVehicleUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void handle(CreateVehicleCommand command) {
        useCase.execute(
                new VehicleId(command.id()),
                new VehicleLicensePlate(command.licensePlate()),
                new VehicleBrand(command.brand()),
                new VehicleModel(command.model()),
                new VehicleYear(command.year()),
                new VehicleColor(command.color()),
                new VehicleType(command.type()),
                new VehicleMileage(command.mileage())
        );
    }
}
