package com.henrierasmus.leanstack.git.cli.command.plumbing;

import com.henrierasmus.leanstack.git.cli.command.Command;
import com.henrierasmus.leanstack.git.cli.command.CommandContext;
import com.henrierasmus.leanstack.git.cli.command.CommandFactory;
import com.henrierasmus.leanstack.git.cli.runtime.RuntimeContext;

import java.io.IOException;

public class WriteTreeCommand implements Command {
    private final CommandContext args;
    private final RuntimeContext ctx;

    public WriteTreeCommand(CommandContext args, RuntimeContext ctx) {
        this.args = args;
        this.ctx = ctx;
    }

    @Override
    public String execute() throws IOException {
        return ctx.objectStore().writeTree().getHex();
    }

    public static CommandFactory factory() {
        return WriteTreeCommand::new;
    }
}
