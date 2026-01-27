package com.henrierasmus.leanstack.git.cli.command.plumbing;

import com.henrierasmus.leanstack.git.cli.command.Command;
import com.henrierasmus.leanstack.git.cli.command.CommandContext;
import com.henrierasmus.leanstack.git.cli.command.CommandFactory;
import com.henrierasmus.leanstack.git.domain.ObjectType;
import com.henrierasmus.leanstack.git.ports.ObjectStore;

import java.io.IOException;

public class ReadTreeCommand implements Command {
    private final ObjectStore objectStore;
    private final CommandContext args;

    public ReadTreeCommand(CommandContext args, ObjectStore objectStore) {
        this.objectStore = objectStore;
        this.args = args;
    }

    @Override
    public String execute() throws IOException {
        System.out.println(args.toString());
        if (args.getArguments().isEmpty()) {
            throw new IllegalArgumentException("No tree object provided for 'read-tree' command");
        }

        if (!objectStore.validateObjectType(args.getArguments().getFirst(), ObjectType.TREE)) {
            throw new IllegalArgumentException("Only 'tree' objects can be added to index using 'read-tree'");
        }

        String prefix = args.options().get("prefix");

        if (prefix == null) {
            throw new IllegalArgumentException("Prefix option is required for 'read-tree' command");
        }

        String data = "tree " + args.getArguments().getFirst() + " " + prefix  + "\n";

        objectStore.updateIndex(System.getProperty("user.dir"), data);

        return "Read tree";
    }

    public static CommandFactory factory() {
        return (args, ctx) -> new ReadTreeCommand(args, ctx.objectStore());
    }
}
