package com.henrierasmus.leanstack.git.cli.command;

import java.util.List;

public class CommandContext {
    private final String commandName;
    private final List<String> arguments;
    private final List<String> options;

    public CommandContext(String command, List<String> arguments, List<String> options) {
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

    public List<String> getOptions() {
        return options;
    }
}
