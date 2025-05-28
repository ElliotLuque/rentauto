package com.rentauto.shared.infrastructure.bus.command;

import com.rentauto.shared.domain.bus.command.Command;
import com.rentauto.shared.domain.bus.command.CommandBus;
import com.rentauto.shared.domain.bus.command.CommandHandler;
import com.rentauto.shared.domain.bus.command.CommandHandlerExecutionError;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public final class InMemoryCommandBus implements CommandBus {
    private final ApplicationContext context;
    private final CommandHandlersInformation information;

    public InMemoryCommandBus(ApplicationContext context, CommandHandlersInformation information) {
        this.context = context;
        this.information = information;
    }

    @Override
    public void dispatch(Command command) throws CommandHandlerExecutionError {
        try {
            Class<? extends CommandHandler> commandHandlerClass = information.search(command.getClass());

            CommandHandler handler = context.getBean(commandHandlerClass);

            handler.handle(command);
        } catch (Throwable e) {
            throw new CommandHandlerExecutionError(e);
        }
    }
}
