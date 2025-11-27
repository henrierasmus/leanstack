package com.henrierasmus.leanstack.git.cli;

import com.henrierasmus.leanstack.git.cli.error.CommandExecutionException;
import com.henrierasmus.leanstack.git.cli.error.CommandNotFoundException;
import com.henrierasmus.leanstack.git.cli.runtime.CommandRegistry;
import com.henrierasmus.leanstack.git.cli.runtime.Runner;
import com.henrierasmus.leanstack.git.cli.runtime.RuntimeContext;
import com.henrierasmus.leanstack.git.ports.ObjectStore;
import com.henrierasmus.leanstack.git.usecase.InitRepository;
import com.henrierasmus.leanstack.logger.LoggerConfig;
import com.henrierasmus.leanstack.logger.Logger;
import com.henrierasmus.leanstack.logger.LoggerFactory;
import com.henrierasmus.leanstack.logger.LoggerType;
import com.henrierasmus.leanstack.logger.internal.LoggerFactoryImpl;

import java.util.ServiceLoader;

public class Main {
    private static final String DEFAULT_REPO_LAYOUT_PROVIDER = "com.henrierasmus.leanstack.git.RepoLayoutImpl";
    private static final String DEFAULT_OBJECT_STORE_PROVIDER = "com.henrierasmus.leanstack.git.ObjectStoreImpl";
    private static final String DEFAULT_REF_STORE_PROVIDER = "com.henrierasmus.leanstack.git.RefStoreImpl";

    private static final ServiceLoader<ObjectStore> objectStoreLoader = ServiceLoader.load(ObjectStore.class);
    private static final InitRepository initRepo = InitRepository.getInstance();

    private static final ObjectStore objectStore = (ObjectStore) initStore(objectStoreLoader, DEFAULT_OBJECT_STORE_PROVIDER);
    private static final RuntimeContext ctx = new RuntimeContext(System.getProperty("user.dir"), objectStore, initRepo);

    public static void main(String[] args) {
        String loggingPath = System.getProperty("user.dir");
        LoggerConfig loggerConfig = new LoggerConfig(LoggerType.FILE, loggingPath);
        LoggerFactory loggerFactory = new LoggerFactoryImpl();
        Logger logger = loggerFactory.getLogger("Main.java", loggerConfig);

        logger.info("Application started");
        CommandRegistry registry = new CommandRegistry(ctx, loggerConfig);
        Runner runner = new Runner(registry, loggerConfig, ctx);

        if (args == null || args[0] == null) {
            System.out.println("No command provided to execute");
            System.exit(127);
        }

        try {
            System.out.println(runner.execute(args));
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

    public static <T> Object initStore(ServiceLoader<T> loader, String provider) {
        return loader.stream()
                .filter(p -> p.type().getName().equals(provider))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Provider not found: " + provider))
                .get();
    }
}