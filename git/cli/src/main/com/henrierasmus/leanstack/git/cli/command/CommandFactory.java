package com.henrierasmus.leanstack.git.cli.command;

import com.henrierasmus.leanstack.git.cli.error.CommandNotFoundException;

/**
 * Generic factory to create Command
 **/
public interface CommandFactory {
    /**
     * Creates Command from the provided token
     *
     * @param token - Class pointer for the Command
     * @throws NoSuchMethodException - Method was not found from the constructor
     * @throws CommandNotFoundException - Could not find the command by the Token
     */
    Command make(Class<? extends Command> token) throws NoSuchMethodException, CommandNotFoundException;
}
