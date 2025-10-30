package com.henrierasmus.leanstack.git.cli.command;

import com.henrierasmus.leanstack.git.cli.error.CommandNotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CommandFactoryImpl implements CommandFactory {
    public CommandFactoryImpl() {}

    public Command make(Class<? extends Command> token) throws NoSuchMethodException, CommandNotFoundException {
        Command command;
        Constructor<? extends Command> constructor = token.getConstructor();

        try {
            command = constructor.newInstance();
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e.getMessage());
            // TODO handle this exception better, maybe create another
            throw new CommandNotFoundException("test");
        }

        return command;
    }
}
