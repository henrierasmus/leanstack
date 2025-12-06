package com.henrierasmus.leanstack.git.cli.command;

import java.io.IOException;

/**
 * Command Interfaces that manages the commands to be executed.
 */
public interface Command {
    /**
     * Execute the command
     */
    String execute() throws IOException;
}
