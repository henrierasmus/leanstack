package com.henrierasmus.leanstack.git.cli.runtime;

import com.henrierasmus.leanstack.git.cli.command.AbstractCommand;
import com.henrierasmus.leanstack.git.cli.command.Command;
import com.henrierasmus.leanstack.git.cli.command.CommandFactory;
import com.henrierasmus.leanstack.git.cli.command.CommandFactoryImpl;
import com.henrierasmus.leanstack.git.cli.error.CommandExecutionException;
import com.henrierasmus.leanstack.git.cli.error.CommandNotFoundException;
import com.henrierasmus.leanstack.logger.LoggerConfig;
import com.henrierasmus.leanstack.logger.Logger;
import com.henrierasmus.leanstack.logger.internal.FileLogger;

public class Runner {
    public CommandRegistry registry;
    private final CommandFactory factory;;
    private final Logger logger;

    public Runner(CommandRegistry registry, LoggerConfig loggerConfig) {
        this.logger = new FileLogger("Runner", loggerConfig);
        this.factory = new CommandFactoryImpl(loggerConfig);
        this.registry = registry;
    }

    public void execute(String[] args) throws CommandNotFoundException, CommandExecutionException {
        String commandName = args[0];
        logger.info("execute: " + commandName);
        Class<? extends AbstractCommand> response = registry.get(commandName);

        if (response == null) throw new CommandNotFoundException(commandName);

        try {
            Command command = factory.make(response, commandName);
            command.execute();
        } catch (CommandExecutionException e) {
            logger.error("Command not found: ", e);
            throw new CommandNotFoundException(commandName);
        }
    }
}
