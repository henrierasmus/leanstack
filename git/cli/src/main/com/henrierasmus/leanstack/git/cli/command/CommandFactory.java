package com.henrierasmus.leanstack.git.cli.command;

public interface CommandFactory {
    Command make(Class<? extends Command> token);
}
