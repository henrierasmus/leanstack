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
        /*
            1. Get index location
            2. Loop over files
            3. build tree a tree for every directory

            What does a tree look like?
            File with a list of object references:
                Hash - ABCD10238904532849032
                Type - Blob/Tree
                Name - File name?
         */
        ctx.objectStore().writeTree();
        return "";
    }

    public static CommandFactory factory() {
        return WriteTreeCommand::new;
    }
}
