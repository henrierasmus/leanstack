package com.henrierasmus.leanstack.git.cli.error;

public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String command) {
        super(command + " command not found");
    }
}
