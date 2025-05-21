package invoices.application.create;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import invoices.domain.InvoiceId;

public final class CreateInvoiceCommandHandler implements CommandHandler<CreateInvoiceCommand> {
    private final CreateInvoiceUseCase useCase;

    public CreateInvoiceCommandHandler(CreateInvoiceUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void handle(CreateInvoiceCommand command) {
        useCase.execute(
                command.id(),
                command.contractId(),
                command.issueDate(),
                command.lines(),
                command.discounts(),
                command.taxes()
        );
    }
}