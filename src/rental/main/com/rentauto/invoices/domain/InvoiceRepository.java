package com.rentauto.invoices.domain;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Invoice aggregate
 */
public interface InvoiceRepository {
    /**
     * Save an invoice
     * @param invoice The invoice to save
     */
    void save(Invoice invoice);

    /**
     * Find an invoice by its ID
     * @param id The invoice ID
     * @return The invoice if found
     */
    Optional<Invoice> findById(InvoiceId id);

    /**
     * Find all invoices for a contract
     * @param contractId The contract ID
     * @return List of invoices
     */
    List<Invoice> findByContractId(InvoiceContractId contractId);

    /**
     * Find all invoices with a specific payment status
     * @param paymentStatus The invoice payment status
     * @return List of invoices
     */
    List<Invoice> findByPaymentStatus(InvoicePaymentStatus paymentStatus);
}
