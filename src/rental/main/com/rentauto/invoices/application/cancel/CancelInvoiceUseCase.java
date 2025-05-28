package com.rentauto.invoices.application.cancel;

import com.rentauto.invoices.domain.Invoice;
import com.rentauto.invoices.domain.InvoiceId;
import com.rentauto.invoices.domain.InvoiceRepository;

/**
 * Use case for canceling an invoice
 */
public final class CancelInvoiceUseCase {
    private final InvoiceRepository repository;

    public CancelInvoiceUseCase(InvoiceRepository repository) {
        this.repository = repository;
    }

    /**
     * Cancel an invoice
     * @param id The invoice ID
     * @param reason The reason for cancellation
     * @param generatesRectification Whether to generate a rectification invoice
     */
    public void execute(InvoiceId id, String reason, boolean generatesRectification) {
        // Find the invoice
        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

        // Cancel the invoice
        Invoice canceledInvoice = invoice.cancel(reason, generatesRectification);

        // Save the canceled invoice
        repository.save(canceledInvoice);
    }
}