package com.henrierasmus.leanstack.git.cli;

import com.henrierasmus.leanstack.git.cli.error.CommandNotFoundException;
import com.henrierasmus.leanstack.git.cli.runtime.CommandRegistry;

public class Main {
    public static void main(String[] args) {
        CommandRegistry registry = CommandRegistry.getInstance();

        if (args == null || args[0] == null) {
            System.out.println("No command provided to execute");
            System.exit(1);
        }

        try {
            registry.execute(args[0]);
        } catch (CommandNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}