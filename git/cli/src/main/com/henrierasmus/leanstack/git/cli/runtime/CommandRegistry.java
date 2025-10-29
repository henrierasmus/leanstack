package com.henrierasmus.leanstack.git.cli.runtime;

import java.util.Map;
import java.util.HashMap;

import com.henrierasmus.leanstack.git.cli.command.plumbing.*;
import com.henrierasmus.leanstack.git.cli.error.CommandNotFoundException;

public class CommandRegistry {
    private static CommandRegistry INSTANCE;
    private final Map<String, Class<?>> REGISTER = new HashMap<>();

    private CommandRegistry() {
        initRegister();
    }

    public static CommandRegistry getRegistry() {
        if (INSTANCE == null) {
            INSTANCE = new CommandRegistry();
        }

        return INSTANCE;
    }

    // Instead of void a Response object should be returned
    public void execute(String command) throws CommandNotFoundException {
        Class<?> response = REGISTER.get(command);
        // Now the factory should be used to create the command/commandType

        if (response == null) throw new CommandNotFoundException(command);

        // TODO: A better logging solution needs to be implemented
        System.out.println(response);
    }

    // TODO: This should return actual command classes that will be executed
    private void initRegister() {
        REGISTER.put("init", InitCommand.class);
//        REGISTER.put("hash-object", HashObjectCommand.class);
//        REGISTER.put("write-tree", WriteTreeCommand.class);
//        REGISTER.put("read-tree", ReadTreeCommand.class);
//        REGISTER.put("commit-tree", CommitTreeCommand.class);
    }
}
