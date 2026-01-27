package com.henrierasmus.leanstack.git.cli.command;

import java.util.List;
import java.util.Map;

public class CommandContext {
    private final String commandName;
    private final List<String> arguments;
    private final Map<String, String> options;

    public CommandContext(String command, List<String> arguments, Map<String, String> options) {
        this.commandName = command;
        this.arguments = arguments;
        this.options = options;
    }

    public String getCommandName() {
        return commandName;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public Map<String, String> options() {
        return options;
    }

    @Override
    public String toString() {
        return commandName + " " + arguments + " " + options;
    }
}
