package com.henrierasmus.leanstack.git.cli.runtime;

import java.util.Map;
import java.util.HashMap;

import com.henrierasmus.leanstack.git.cli.command.*;
import com.henrierasmus.leanstack.git.cli.command.plumbing.*;

public class CommandRegistry {
    private final Map<String, Class<? extends AbstractCommand>> REGISTRY = new HashMap<>();

    public CommandRegistry() {
        initRegister();
    }

    public Class<? extends AbstractCommand> get(String token) {
        return REGISTRY.get(token);
    }

    private void initRegister() {
        REGISTRY.put("init", InitCommand.class);
        REGISTRY.put("hash-object", HashObjectCommand.class);
        REGISTRY.put("write-tree", WriteTreeCommand.class);
        REGISTRY.put("read-tree", ReadTreeCommand.class);
        REGISTRY.put("commit-tree", CommitTreeCommand.class);
    }
}
