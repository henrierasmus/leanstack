package com.henrierasmus.leanstack.git.cli.command.plumbing;

import com.henrierasmus.leanstack.git.cli.command.AbstractCommand;

public class HashObjectCommand extends AbstractCommand {
    public HashObjectCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void execute() {
        System.out.println("HashObject");
    }
}
