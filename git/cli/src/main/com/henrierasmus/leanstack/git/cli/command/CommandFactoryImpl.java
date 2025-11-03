package com.henrierasmus.leanstack.git.cli.command;

import com.henrierasmus.leanstack.git.cli.error.CommandExecutionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CommandFactoryImpl implements CommandFactory {
    public CommandFactoryImpl() {}

    public Command make(Class<? extends AbstractCommand> token, String name) throws CommandExecutionException {
        Command command;

        try {
            Constructor<? extends Command> constructor = token.getConstructor(String.class);
            command = constructor.newInstance(name);
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.out.println(e.getMessage());
            throw new CommandExecutionException("Failed to create command: ");
        }

        return command;
    }
}
