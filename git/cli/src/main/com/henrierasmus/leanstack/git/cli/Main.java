package com.henrierasmus.leanstack.git.cli;

import com.henrierasmus.leanstack.git.cli.error.CommandExecutionException;
import com.henrierasmus.leanstack.git.cli.error.CommandNotFoundException;
import com.henrierasmus.leanstack.git.cli.runtime.CommandRegistry;
import com.henrierasmus.leanstack.git.cli.runtime.Runner;

public class Main {
    public static void main(String[] args) {
        CommandRegistry registry = new CommandRegistry();
        Runner runner = new Runner(registry);

        if (args == null || args[0] == null) {
            System.out.println("No command provided to execute");
            System.exit(127);
        }

        try {
            runner.execute(args);
        } catch (CommandNotFoundException e) {
            System.out.println("Command not found: " + e.getMessage());
            System.exit(127);
        } catch (CommandExecutionException e) {
            System.out.println("Failed to execute command: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            System.exit(1);
        }
    }
}