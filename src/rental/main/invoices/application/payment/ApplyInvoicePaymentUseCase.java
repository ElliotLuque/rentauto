package invoices.application.payment;

import invoices.domain.Invoice;
import invoices.domain.InvoiceId;
import invoices.domain.InvoiceMoney;
import invoices.domain.InvoiceRepository;

/**
 * Use case for applying a payment to an invoice
 */
public final class ApplyInvoicePaymentUseCase {
    private final InvoiceRepository repository;

    public ApplyInvoicePaymentUseCase(InvoiceRepository repository) {
        this.repository = repository;
    }

    /**
     * Apply a payment to an invoice
     * @param id The invoice ID
     * @param amountPaid The amount paid
     */
    public void execute(InvoiceId id, int amountPaid) {
        // Find the invoice
        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

        // Apply the payment
        Invoice paidInvoice = invoice.applyPayment(new InvoiceMoney(amountPaid));

        // Save the paid invoice
        repository.save(paidInvoice);
    }
}