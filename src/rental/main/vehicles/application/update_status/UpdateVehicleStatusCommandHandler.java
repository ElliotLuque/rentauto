package vehicles.application.update_status;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import vehicles.domain.VehicleId;
import vehicles.domain.VehicleStatus;

public final class UpdateVehicleStatusCommandHandler implements CommandHandler<UpdateVehicleStatusCommand> {
    private final UpdateVehicleStatusUseCase useCase;

    public UpdateVehicleStatusCommandHandler(UpdateVehicleStatusUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void handle(UpdateVehicleStatusCommand command) {
        useCase.execute(
                new VehicleId(command.id()),
                new VehicleStatus(command.status())
        );
    }
}