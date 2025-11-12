package com.henrierasmus.leanstack.git.cli.runtime;

import java.util.Map;
import java.util.HashMap;

import com.henrierasmus.leanstack.git.cli.command.*;
import com.henrierasmus.leanstack.git.cli.command.plumbing.*;
import com.henrierasmus.leanstack.logger.LoggerConfig;
import com.henrierasmus.leanstack.logger.Logger;
import com.henrierasmus.leanstack.logger.internal.FileLogger;

public class CommandRegistry {
    private final Map<String, Class<? extends AbstractCommand>> REGISTRY = new HashMap<>();
    private final Logger logger;

    public CommandRegistry(LoggerConfig loggerConfig) {
        this.logger = new FileLogger("CommandRegistry", loggerConfig);
        initRegister();
    }

    public Class<? extends AbstractCommand> get(String token) {
        return REGISTRY.get(token);
    }

    private void initRegister() {
        logger.info("Init Registry");
        REGISTRY.put("init", InitCommand.class);
        REGISTRY.put("hash-object", HashObjectCommand.class);
        REGISTRY.put("write-tree", WriteTreeCommand.class);
        REGISTRY.put("read-tree", ReadTreeCommand.class);
        REGISTRY.put("commit-tree", CommitTreeCommand.class);
    }
}
