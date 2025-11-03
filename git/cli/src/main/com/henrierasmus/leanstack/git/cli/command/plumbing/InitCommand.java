package com.henrierasmus.leanstack.git.cli.command.plumbing;

import com.henrierasmus.leanstack.git.cli.command.AbstractCommand;

public class InitCommand extends AbstractCommand {

    public InitCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void execute() {
        System.out.println("Execute init");
    }
}
