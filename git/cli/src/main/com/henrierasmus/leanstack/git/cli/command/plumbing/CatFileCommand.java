package com.henrierasmus.leanstack.git.cli.command.plumbing;

import com.henrierasmus.leanstack.git.cli.command.Command;
import com.henrierasmus.leanstack.git.cli.command.CommandContext;
import com.henrierasmus.leanstack.git.cli.command.CommandFactory;
import com.henrierasmus.leanstack.git.ports.ObjectStore;

import java.io.IOException;

public class CatFileCommand implements Command {
    private final ObjectStore objectStore;
    private final CommandContext args;

    public CatFileCommand(CommandContext args, ObjectStore objectStore) {
        this.objectStore = objectStore;
        this.args = args;
    }

    @Override
    public String execute() {
        if (args.getArguments() == null || args.getArguments().isEmpty()) throw new IllegalArgumentException("No object provided");
        if (args.getArguments().getFirst().length() < 40) throw new IllegalArgumentException("Invalid object ID");
        try {
            return objectStore.catFile(args.getArguments().getFirst());
        } catch(IOException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Parameters passed does not match");
        }
    }

    public static CommandFactory factory() {
        return (args, ctx) -> new CatFileCommand(args, ctx.objectStore());
    }
}
