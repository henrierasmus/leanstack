package com.henrierasmus.leanstack.git.cli.command;

import com.henrierasmus.leanstack.git.cli.error.CommandExecutionException;

/**
 * Generic factory to create Command
 **/
public interface CommandFactory {
    /**
     * Creates Command from the provided token
     *
     * @param token - Class pointer for the Command
     * @throws CommandExecutionException - Could not execute the command
     */
    Command make(Class<? extends AbstractCommand> token, String name) throws CommandExecutionException;
}
