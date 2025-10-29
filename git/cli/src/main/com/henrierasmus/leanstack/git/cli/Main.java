package com.henrierasmus.leanstack.git.cli;

import com.henrierasmus.leanstack.git.cli.error.CommandNotFoundException;
import com.henrierasmus.leanstack.git.cli.runtime.CommandRegistry;

public class Main {
    static void main(String[] args) {
        CommandRegistry registry = CommandRegistry.getRegistry();

        try {
            registry.execute(args[0]);
        } catch (CommandNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}