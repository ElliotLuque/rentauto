package com.rentauto.invoices.domain;

import com.rentauto.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

public final class InvoiceCreatedDomainEvent extends DomainEvent {
    private final String contractId;
    private final String issueDate;
    private final int grossAmount;
    private final int discounts;
    private final int taxes;
    private final int netAmount;

    public InvoiceCreatedDomainEvent(
            String aggregateId,
            String contractId,
            LocalDate issueDate,
            int grossAmount,
            int discounts,
            int taxes,
            int netAmount
    ) {
        super(aggregateId);
        this.contractId = contractId;
        this.issueDate = issueDate.toString();
        this.grossAmount = grossAmount;
        this.discounts = discounts;
        this.taxes = taxes;
        this.netAmount = netAmount;
    }

    public InvoiceCreatedDomainEvent(
            String aggregateId,
            String contractId,
            String issueDate,
            int grossAmount,
            int discounts,
            int taxes,
            int netAmount,
            String eventId,
            String occurredOn
    ) {
        super(aggregateId, eventId, occurredOn);
        this.contractId = contractId;
        this.issueDate = issueDate;
        this.grossAmount = grossAmount;
        this.discounts = discounts;
        this.taxes = taxes;
        this.netAmount = netAmount;
    }

    @Override
    public String eventName() {
        return "invoice.created";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> primitives = new HashMap<>();
        primitives.put("contractId", contractId);
        primitives.put("issueDate", issueDate);
        primitives.put("grossAmount", grossAmount);
        primitives.put("discounts", discounts);
        primitives.put("taxes", taxes);
        primitives.put("netAmount", netAmount);
        return primitives;
    }

    @Override
    public DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    ) {
        return new InvoiceCreatedDomainEvent(
                aggregateId,
                (String) body.get("contractId"),
                (String) body.get("issueDate"),
                (int) body.get("grossAmount"),
                (int) body.get("discounts"),
                (int) body.get("taxes"),
                (int) body.get("netAmount"),
                eventId,
                occurredOn
        );
    }

    public String contractId() {
        return contractId;
    }

    public String issueDate() {
        return issueDate;
    }

    public int grossAmount() {
        return grossAmount;
    }

    public int discounts() {
        return discounts;
    }

    public int taxes() {
        return taxes;
    }

    public int netAmount() {
        return netAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceCreatedDomainEvent that = (InvoiceCreatedDomainEvent) o;
        return grossAmount == that.grossAmount &&
               discounts == that.discounts &&
               taxes == that.taxes &&
               netAmount == that.netAmount &&
               Objects.equals(aggregateId(), that.aggregateId()) &&
               Objects.equals(contractId, that.contractId) &&
               Objects.equals(issueDate, that.issueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            aggregateId(), contractId, issueDate, grossAmount, discounts, taxes, netAmount
        );
    }
}