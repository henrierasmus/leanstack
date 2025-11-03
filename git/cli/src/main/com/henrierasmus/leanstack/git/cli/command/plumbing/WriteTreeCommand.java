package com.henrierasmus.leanstack.git.cli.command.plumbing;

import com.henrierasmus.leanstack.git.cli.command.AbstractCommand;

public class WriteTreeCommand extends AbstractCommand {
    public WriteTreeCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void execute() {
        System.out.println("WriteTree");
    }
}
