package com.henrierasmus.leanstack.git.cli.command.plumbing;

import com.henrierasmus.leanstack.git.cli.command.Command;
import com.henrierasmus.leanstack.git.cli.command.CommandContext;
import com.henrierasmus.leanstack.git.cli.command.CommandFactory;
import com.henrierasmus.leanstack.git.cli.runtime.RuntimeContext;

import java.io.IOException;

public class UpdateIndexCommand implements Command {
    private final CommandContext args;
    private final RuntimeContext ctx;

    public UpdateIndexCommand(CommandContext args, RuntimeContext ctx) {
        if (args.getArguments().size() < 2) throw new IllegalArgumentException("Expected 2 arguments");
        this.args = args;
        this.ctx = ctx;
    }

    @Override
    public String execute() throws IOException {
        // TODO Add some validation, hash length, ensure file extension
        String data = args.getArguments().get(0) + " " + args.getArguments().get(1) + "\n";
        ctx.objectStore().updateIndex(System.getProperty("user.dir"), data);
        return "";
    }

    public static CommandFactory factory() {
        return UpdateIndexCommand::new;
    }
}
