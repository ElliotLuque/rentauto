package com.rentauto.vehicles.application.create;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;
import com.rentauto.vehicles.domain.VehicleBrand;
import com.rentauto.vehicles.domain.VehicleColor;
import com.rentauto.vehicles.domain.VehicleId;
import com.rentauto.vehicles.domain.VehicleLicensePlate;
import com.rentauto.vehicles.domain.VehicleMileage;
import com.rentauto.vehicles.domain.VehicleModel;
import com.rentauto.vehicles.domain.VehicleType;
import com.rentauto.vehicles.domain.VehicleYear;

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
