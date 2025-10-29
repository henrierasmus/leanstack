package com.henrierasmus.leanstack.git.cli.command;

public class CommandFactoryImpl implements CommandFactory {
    /*
    This is going to take a reference to a class as an argument.
    It will use the reference and use reflection to call the class constructor and return the instance just created
    */
    public Command make(Class<? extends Command> clazz) {
        return null;
    }
}
