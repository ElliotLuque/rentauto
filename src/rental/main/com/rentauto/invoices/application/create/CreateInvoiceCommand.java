package com.rentauto.invoices.application.create;

import com.rentauto.shared.domain.bus.command.Command;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public final class CreateInvoiceCommand implements Command {
    private final UUID id;
    private final UUID contractId;
    private final LocalDate issueDate;
    private final List<InvoiceLineDTO> lines;
    private final int discounts;
    private final int taxes;

    public CreateInvoiceCommand(
            UUID id,
            UUID contractId,
            LocalDate issueDate,
            List<InvoiceLineDTO> lines,
            int discounts,
            int taxes
    ) {
        this.id = id;
        this.contractId = contractId;
        this.issueDate = issueDate;
        this.lines = lines;
        this.discounts = discounts;
        this.taxes = taxes;
    }

    public UUID id() {
        return id;
    }

    public UUID contractId() {
        return contractId;
    }

    public LocalDate issueDate() {
        return issueDate;
    }

    public List<InvoiceLineDTO> lines() {
        return lines;
    }

    public int discounts() {
        return discounts;
    }

    public int taxes() {
        return taxes;
    }

    public static final class InvoiceLineDTO {
        private final String description;
        private final int amount;

        public InvoiceLineDTO(String description, int amount) {
            this.description = description;
            this.amount = amount;
        }

        public String description() {
            return description;
        }

        public int amount() {
            return amount;
        }
    }
}