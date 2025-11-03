package com.henrierasmus.leanstack.git.cli.runtime;

import com.henrierasmus.leanstack.git.cli.command.AbstractCommand;
import com.henrierasmus.leanstack.git.cli.command.Command;
import com.henrierasmus.leanstack.git.cli.command.CommandFactory;
import com.henrierasmus.leanstack.git.cli.command.CommandFactoryImpl;
import com.henrierasmus.leanstack.git.cli.error.CommandExecutionException;
import com.henrierasmus.leanstack.git.cli.error.CommandNotFoundException;

public class Runner {
    public CommandRegistry registry;
    private final CommandFactory factory = new CommandFactoryImpl();

    public Runner(CommandRegistry registry) {
        this.registry = registry;
    }

    public void execute(String[] args) throws CommandNotFoundException, CommandExecutionException {
        String commandName = args[0];
        Class<? extends AbstractCommand> response = registry.get(commandName);

        if (response == null) throw new CommandNotFoundException(commandName);

        try {
            Command command = factory.make(response, commandName);
            command.execute();
        } catch (CommandExecutionException e) {
            throw new CommandNotFoundException(commandName);
        }
    }
}
