package com.henrierasmus.leanstack.git.cli.command.plumbing;

import com.henrierasmus.leanstack.git.cli.command.Command;
import com.henrierasmus.leanstack.git.cli.command.CommandContext;
import com.henrierasmus.leanstack.git.cli.command.CommandFactory;
import com.henrierasmus.leanstack.git.cli.runtime.RuntimeContext;

import java.io.IOException;

public class CommitTreeCommand implements Command {
    final private CommandContext args;
    final private RuntimeContext ctx;

    private CommitTreeCommand(CommandContext args, RuntimeContext ctx) {
        this.args = args;
        this.ctx = ctx;
    }
    /*
        1. Command takes a tree Object Id and a message
        2. Create a new git object (commit object)
     */
    public String execute() throws IOException {
        ctx.objectStore().commitTree("test", "test");
        return "CommitTree";
    }

    public static CommandFactory factory() {
        return CommitTreeCommand::new;
    }
}
