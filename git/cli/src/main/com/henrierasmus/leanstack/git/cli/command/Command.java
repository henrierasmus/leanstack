package com.henrierasmus.leanstack.git.cli.command;

import com.henrierasmus.leanstack.git.domain.ObjectId;

/**
 * Command Interfaces that manages the commands to be executed.
 */
public interface Command {
    /**
     * Execute the command
     */
    String execute();
}
