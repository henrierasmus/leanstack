package com.henrierasmus.leanstack.git.cli;

import com.henrierasmus.leanstack.git.cli.error.CommandExecutionException;
import com.henrierasmus.leanstack.git.cli.error.CommandNotFoundException;
import com.henrierasmus.leanstack.git.cli.runtime.CommandRegistry;
import com.henrierasmus.leanstack.git.cli.runtime.Runner;
import com.henrierasmus.leanstack.logger.LoggerConfig;
import com.henrierasmus.leanstack.logger.Logger;
import com.henrierasmus.leanstack.logger.LoggerFactory;
import com.henrierasmus.leanstack.logger.LoggerType;
import com.henrierasmus.leanstack.logger.internal.LoggerFactoryImpl;

public class Main {
    public static void main(String[] args) {
        String loggingPath = System.getProperty("user.dir");
        LoggerConfig loggerConfig = new LoggerConfig(LoggerType.FILE, loggingPath);
        LoggerFactory loggerFactory = new LoggerFactoryImpl();
        Logger logger = loggerFactory.getLogger("Main.java", loggerConfig);

        logger.info("Application started");
        CommandRegistry registry = new CommandRegistry(loggerConfig);
        Runner runner = new Runner(registry, loggerConfig);

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