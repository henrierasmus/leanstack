package com.henrierasmus.leanstack.git.cli.runtime;

import com.henrierasmus.leanstack.git.cli.command.Command;
import com.henrierasmus.leanstack.git.cli.command.CommandContext;
import com.henrierasmus.leanstack.git.cli.command.CommandFactory;
import com.henrierasmus.leanstack.git.cli.error.CommandExecutionException;
import com.henrierasmus.leanstack.git.cli.error.CommandNotFoundException;
import com.henrierasmus.leanstack.git.cli.parser.Parser;
import com.henrierasmus.leanstack.git.domain.ObjectId;
import com.henrierasmus.leanstack.logger.LoggerConfig;
import com.henrierasmus.leanstack.logger.Logger;
import com.henrierasmus.leanstack.logger.internal.FileLogger;

public class Runner {
    public CommandRegistry registry;
    private final Logger logger;
    private final RuntimeContext ctx;
    private Parser parser = new Parser();

    public Runner(CommandRegistry registry, LoggerConfig loggerConfig, RuntimeContext ctx) {
        this.logger = new FileLogger("Runner", loggerConfig);
        this.registry = registry;
        this.ctx = ctx;
    }

    public String execute(String[] args) throws CommandNotFoundException, CommandExecutionException {
        CommandContext commandContext = parse(args);
        logger.info("execute: " + commandContext.getCommandName());
        CommandFactory factory = registry.get(commandContext.getCommandName());

        if (factory == null) throw new CommandNotFoundException(commandContext.getCommandName());

        try {
            Command command = factory.make(commandContext, ctx);
            return command.execute();
        } catch (CommandExecutionException e) {
            logger.error("Command not found: ", e);
            throw new CommandNotFoundException(commandContext.getCommandName());
        }
    }

    private CommandContext parse(String[] args) {
        return parser.parse(args);
    }
}
