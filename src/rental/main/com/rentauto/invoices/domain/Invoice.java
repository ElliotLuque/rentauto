package com.rentauto.invoices.domain;

import com.rentauto.shared.domain.AggregateRoot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Invoice extends AggregateRoot {
    private final InvoiceId id;
    private final InvoiceContractId contractId;
    private final InvoiceIssueDate issueDate;
    private final List<InvoiceLine> lines;
    private final InvoiceMoney discounts;
    private final InvoiceMoney taxes;
    private final InvoiceMoney grossAmount;
    private final InvoiceMoney netAmount;
    private final InvoicePaymentStatus paymentStatus;

    public Invoice(
            InvoiceId id,
            InvoiceContractId contractId,
            InvoiceIssueDate issueDate,
            List<InvoiceLine> lines,
            InvoiceMoney discounts,
            InvoiceMoney taxes,
            InvoicePaymentStatus paymentStatus
    ) {
        this.id = id;
        this.contractId = contractId;
        this.issueDate = issueDate;
        this.lines = new ArrayList<>(lines);
        this.discounts = discounts;
        this.taxes = taxes;
        this.grossAmount = calculateGrossAmount(lines);
        this.netAmount = calculateNetAmount(this.grossAmount, discounts, taxes);
        this.paymentStatus = paymentStatus;
        
        validateNetAmount();
    }

    public static Invoice create(
            InvoiceId id,
            InvoiceContractId contractId,
            InvoiceIssueDate issueDate,
            List<InvoiceLine> lines,
            InvoiceMoney discounts,
            InvoiceMoney taxes
    ) {
        Invoice invoice = new Invoice(
                id,
                contractId,
                issueDate,
                lines,
                discounts,
                taxes,
                InvoicePaymentStatus.pending()
        );
        
        invoice.record(new InvoiceCreatedDomainEvent(
                id.value().toString(),
                contractId.value().toString(),
                issueDate.value(),
                invoice.grossAmount().value(),
                discounts.value(),
                taxes.value(),
                invoice.netAmount().value()
        ));
        
        return invoice;
    }

    public Invoice applyPayment(InvoiceMoney amountPaid) {
        if (paymentStatus.isPaid()) {
            throw new IllegalStateException("Cannot pay an invoice that is already paid");
        }
        
        if (paymentStatus.isCanceled()) {
            throw new IllegalStateException("Cannot pay a canceled invoice");
        }
        
        if (amountPaid.value() > netAmount.value()) {
            throw new IllegalArgumentException("The paid amount cannot be greater than the net amount");
        }
        
        Invoice paidInvoice = new Invoice(
                id,
                contractId,
                issueDate,
                lines,
                discounts,
                taxes,
                InvoicePaymentStatus.paid()
        );
        
        paidInvoice.record(new InvoicePaidDomainEvent(
                id.value().toString(),
                amountPaid.value()
        ));
        
        return paidInvoice;
    }

    public Invoice cancel(String reason, boolean generatesRectification) {
        if (paymentStatus.isCanceled()) {
            throw new IllegalStateException("The invoice is already canceled");
        }
        
        Invoice canceledInvoice = new Invoice(
                id,
                contractId,
                issueDate,
                lines,
                discounts,
                taxes,
                InvoicePaymentStatus.canceled()
        );
        
        canceledInvoice.record(new InvoiceCanceledDomainEvent(
                id.value().toString(),
                reason,
                generatesRectification
        ));
        
        return canceledInvoice;
    }

    private InvoiceMoney calculateGrossAmount(List<InvoiceLine> lines) {
        return lines.stream()
                .map(InvoiceLine::amount)
                .reduce(InvoiceMoney.zero(), InvoiceMoney::add);
    }

    private InvoiceMoney calculateNetAmount(InvoiceMoney grossAmount, InvoiceMoney discounts, InvoiceMoney taxes) {
        return grossAmount.subtract(discounts).add(taxes);
    }

    private void validateNetAmount() {
        InvoiceMoney calculated = calculateNetAmount(grossAmount, discounts, taxes);
        if (calculated.value() != netAmount.value()) {
            throw new IllegalStateException("The net amount must be equal to grossAmount - discounts + taxes");
        }
    }

    public InvoiceId id() {
        return id;
    }

    public InvoiceContractId contractId() {
        return contractId;
    }

    public InvoiceIssueDate issueDate() {
        return issueDate;
    }

    public List<InvoiceLine> lines() {
        return Collections.unmodifiableList(lines);
    }

    public InvoiceMoney discounts() {
        return discounts;
    }

    public InvoiceMoney taxes() {
        return taxes;
    }

    public InvoiceMoney grossAmount() {
        return grossAmount;
    }

    public InvoiceMoney netAmount() {
        return netAmount;
    }

    public InvoicePaymentStatus paymentStatus() {
        return paymentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}