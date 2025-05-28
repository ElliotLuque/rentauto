package com.rentauto.invoices.application.payment;

import com.rentauto.shared.domain.bus.command.CommandHandler;
import com.rentauto.invoices.domain.InvoiceId;

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