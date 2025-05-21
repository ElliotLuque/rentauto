package invoices.application.payment;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import invoices.domain.InvoiceId;

import java.util.UUID;

public final class ApplyInvoicePaymentCommandHandler implements CommandHandler<ApplyInvoicePaymentCommand> {
    private final ApplyInvoicePaymentUseCase useCase;

    public ApplyInvoicePaymentCommandHandler(ApplyInvoicePaymentUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void handle(ApplyInvoicePaymentCommand command) {
        InvoiceId id = new InvoiceId(command.id());
        int amountPaid = command.amountPaid();

        useCase.execute(id, amountPaid);
    }
}