package com.henrierasmus.leanstack.git.cli.command;

import com.henrierasmus.leanstack.git.cli.error.CommandExecutionException;
import com.henrierasmus.leanstack.git.cli.runtime.RuntimeContext;

/**
 * Generic factory to create Command
 **/
public interface CommandFactory {
    /**
     * Creates Command from the provided token
     *
     * @throws CommandExecutionException - Could not execute the command
     */
    Command make(CommandContext args, RuntimeContext ctx) throws CommandExecutionException;
}
