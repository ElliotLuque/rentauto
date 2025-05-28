package com.rentauto.invoices.application.payment;

import com.rentauto.shared.domain.bus.command.Command;

import java.util.UUID;

public final class ApplyInvoicePaymentCommand implements Command {
    private final UUID id;
    private final int amountPaid;

    public ApplyInvoicePaymentCommand(UUID id, int amountPaid) {
        this.id = id;
        this.amountPaid = amountPaid;
    }

    public UUID id() {
        return id;
    }

    public int amountPaid() {
        return amountPaid;
    }
}