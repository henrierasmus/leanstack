package com.henrierasmus.leanstack.git.cli.command;

import com.henrierasmus.leanstack.git.cli.error.CommandExecutionException;
import com.henrierasmus.leanstack.logger.Logger;
import com.henrierasmus.leanstack.logger.LoggerConfig;
import com.henrierasmus.leanstack.logger.internal.FileLogger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CommandFactoryImpl implements CommandFactory {
    private final Logger logger;

    public CommandFactoryImpl(LoggerConfig loggerConfig) {
        this.logger = new FileLogger("CommandFactoryImpl", loggerConfig);
    }

    public Command make(Class<? extends AbstractCommand> token, String name) throws CommandExecutionException {
        Command command;

        try {
            Constructor<? extends Command> constructor = token.getConstructor(String.class);
            command = constructor.newInstance(name);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException |
                 NoSuchMethodException e) {
            logger.error("Error creating command: ", e);
            throw new CommandExecutionException("Failed to create command: " + name, e);
        }

        return command;
    }
}
