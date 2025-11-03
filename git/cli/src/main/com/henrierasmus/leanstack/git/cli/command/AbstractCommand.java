package com.henrierasmus.leanstack.git.cli.command;

public abstract class AbstractCommand implements Command {
    private final String commandName;

    public AbstractCommand(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
