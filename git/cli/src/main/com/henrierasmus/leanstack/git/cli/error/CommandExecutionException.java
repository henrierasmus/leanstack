package com.henrierasmus.leanstack.git.cli.error;

public class CommandExecutionException extends Exception {
    public CommandExecutionException(String message) {
        super(message);
    }

    public CommandExecutionException(String message, Throwable e) {
        super(message, e);
    }
}
