package com.henrierasmus.leanstack.git.cli.runtime;

import java.util.Map;
import java.util.HashMap;

import com.henrierasmus.leanstack.git.cli.command.*;
import com.henrierasmus.leanstack.git.cli.command.plumbing.*;
import com.henrierasmus.leanstack.git.cli.error.CommandNotFoundException;

public class CommandRegistry {
    private static CommandRegistry INSTANCE;
    private final Map<String, Class<? extends Command>> REGISTRY = new HashMap<>();
    private final CommandFactory factory = new CommandFactoryImpl();

    private CommandRegistry() {
        initRegister();
    }

    public static CommandRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommandRegistry();
        }

        return INSTANCE;
    }

    // TODO: Consider breaking this out to its own class. Runner - Will be responsible for Running all commands with additional arguments
    public void execute(String token) throws CommandNotFoundException {
        Class<? extends Command> response = REGISTRY.get(token);

        if (response == null) throw new CommandNotFoundException(token);

        try {
            Command command = factory.make(response);
            command.execute();
        } catch (CommandNotFoundException | NoSuchMethodException e) {
            throw new CommandNotFoundException(token);
        }
    }

    private void initRegister() {
        REGISTRY.put("init", InitCommand.class);
        REGISTRY.put("hash-object", HashObjectCommand.class);
        REGISTRY.put("write-tree", WriteTreeCommand.class);
        REGISTRY.put("read-tree", ReadTreeCommand.class);
        REGISTRY.put("commit-tree", CommitTreeCommand.class);
    }
}
