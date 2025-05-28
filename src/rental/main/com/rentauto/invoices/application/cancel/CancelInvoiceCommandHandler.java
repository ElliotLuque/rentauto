package com.rentauto.invoices.application.cancel;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import com.rentauto.invoices.domain.InvoiceId;

public final class CancelInvoiceCommandHandler implements CommandHandler<CancelInvoiceCommand> {
    private final CancelInvoiceUseCase useCase;

    public CancelInvoiceCommandHandler(CancelInvoiceUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void handle(CancelInvoiceCommand command) {
        InvoiceId id = new InvoiceId(command.id());
        String reason = command.reason();
        boolean generatesRectification = command.generatesRectification();

        useCase.execute(id, reason, generatesRectification);
    }
}