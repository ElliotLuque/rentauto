package com.rentauto.invoices.application.create;

import com.rentauto.invoices.domain.Invoice;
import com.rentauto.invoices.domain.InvoiceContractId;
import com.rentauto.invoices.domain.InvoiceId;
import com.rentauto.invoices.domain.InvoiceIssueDate;
import com.rentauto.invoices.domain.InvoiceLine;
import com.rentauto.invoices.domain.InvoiceMoney;
import com.rentauto.invoices.domain.InvoiceRepository;
import invoices.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Use case for creating a new invoice
 */
public final class CreateInvoiceUseCase {
    private final InvoiceRepository repository;

    public CreateInvoiceUseCase(InvoiceRepository repository) {
        this.repository = repository;
    }

    /**
     * Create a new invoice
     * @param id The invoice ID
     * @param contractId The contract ID
     * @param issueDate The issue date
     * @param lines The invoice lines
     * @param discounts The discounts amount
     * @param taxes The taxes amount
     * @return The ID of the created invoice
     */
    public InvoiceId execute(
            UUID id,
            UUID contractId,
            LocalDate issueDate,
            List<CreateInvoiceCommand.InvoiceLineDTO> lines,
            int discounts,
            int taxes
    ) {
        // Create invoice ID
        InvoiceId invoiceId = new InvoiceId(id);
        
        // Create contract ID
        InvoiceContractId invoiceContractId = new InvoiceContractId(contractId);
        
        // Create issue date
        InvoiceIssueDate invoiceIssueDate = new InvoiceIssueDate(issueDate);
        
        // Create invoice lines
        List<InvoiceLine> invoiceLines = lines.stream()
                .map(line -> new InvoiceLine(
                        line.description(),
                        new InvoiceMoney(line.amount())
                ))
                .collect(Collectors.toList());
        
        // Create discounts and taxes
        InvoiceMoney invoiceDiscounts = new InvoiceMoney(discounts);
        InvoiceMoney invoiceTaxes = new InvoiceMoney(taxes);
        
        // Create invoice
        Invoice invoice = Invoice.create(
                invoiceId,
                invoiceContractId,
                invoiceIssueDate,
                invoiceLines,
                invoiceDiscounts,
                invoiceTaxes
        );
        
        // Save invoice
        repository.save(invoice);
        
        return invoiceId;
    }
}