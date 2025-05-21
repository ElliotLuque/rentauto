package invoices.application.cancel;

import com.rentauto.shared.domain.bus.command.Command;

import java.util.UUID;

public final class CancelInvoiceCommand implements Command {
    private final UUID id;
    private final String reason;
    private final boolean generatesRectification;

    public CancelInvoiceCommand(UUID id, String reason, boolean generatesRectification) {
        this.id = id;
        this.reason = reason;
        this.generatesRectification = generatesRectification;
    }

    public UUID id() {
        return id;
    }

    public String reason() {
        return reason;
    }

    public boolean generatesRectification() {
        return generatesRectification;
    }
}