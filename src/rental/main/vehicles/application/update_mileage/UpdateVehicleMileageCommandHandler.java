package vehicles.application.update_mileage;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import vehicles.domain.VehicleId;
import vehicles.domain.VehicleMileage;

public final class UpdateVehicleMileageCommandHandler implements CommandHandler<UpdateVehicleMileageCommand> {
    private final UpdateVehicleMileageUseCase useCase;

    public UpdateVehicleMileageCommandHandler(UpdateVehicleMileageUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void handle(UpdateVehicleMileageCommand command) {
        useCase.execute(
                new VehicleId(command.id()),
                new VehicleMileage(command.mileage())
        );
    }
}