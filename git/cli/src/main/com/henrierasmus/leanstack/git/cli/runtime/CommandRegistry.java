package com.henrierasmus.leanstack.git.cli.runtime;

import java.util.Map;
import java.util.HashMap;

import com.henrierasmus.leanstack.git.cli.command.*;
import com.henrierasmus.leanstack.git.cli.command.plumbing.*;
import com.henrierasmus.leanstack.logger.LoggerConfig;
import com.henrierasmus.leanstack.logger.Logger;
import com.henrierasmus.leanstack.logger.internal.FileLogger;

public class CommandRegistry {
    private final Map<String, CommandFactory> REGISTRY = new HashMap<>();
    private final Logger logger;
    private final RuntimeContext ctx;

    public CommandRegistry(RuntimeContext ctx, LoggerConfig loggerConfig) {
        this.logger = new FileLogger("CommandRegistry", loggerConfig);
        this.ctx = ctx;
        initRegister();
    }

    public CommandFactory get(String token) {
        return REGISTRY.get(token);
    }

    private void initRegister() {
        logger.info("Init Registry");
        REGISTRY.put("init", InitCommand.factory());
        REGISTRY.put("hash-object", HashObjectCommand.factory());
//        REGISTRY.put("write-tree", WriteTreeCommand.class);
//        REGISTRY.put("read-tree", ReadTreeCommand.class);
//        REGISTRY.put("commit-tree", CommitTreeCommand.class);
    }
}
