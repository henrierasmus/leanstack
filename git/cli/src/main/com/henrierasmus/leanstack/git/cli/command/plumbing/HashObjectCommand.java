package com.henrierasmus.leanstack.git.cli.command.plumbing;

import com.henrierasmus.leanstack.git.cli.command.Command;
import com.henrierasmus.leanstack.git.cli.command.CommandContext;
import com.henrierasmus.leanstack.git.cli.command.CommandFactory;
import com.henrierasmus.leanstack.git.domain.ObjectType;
import com.henrierasmus.leanstack.git.ports.ObjectStore;

import java.io.IOException;

public class HashObjectCommand implements Command {
    private final ObjectStore objectStore;
    private final CommandContext args;

    public HashObjectCommand(CommandContext args, ObjectStore objectStore) {
        this.args = args;
        this.objectStore = objectStore;
    }

    @Override
    public String execute() {
        if (args.getArguments() == null || args.getArguments().isEmpty()) {
            throw new IllegalArgumentException("No file provided");
        }

        String filePath = args.getArguments().getFirst();
        try {
            return objectStore.storeObject(filePath, ObjectType.BLOB).getHex();
        } catch(IOException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Something went wrong");
        }
    }

    public static CommandFactory factory() {
        return (args, ctx) -> new HashObjectCommand(args, ctx.objectStore());
    }
}
