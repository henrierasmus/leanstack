package com.henrierasmus.leanstack.logger.internal;

import com.henrierasmus.leanstack.logger.LoggerConfig;
import com.henrierasmus.leanstack.logger.LogLevel;
import com.henrierasmus.leanstack.logger.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileLogger implements Logger {
    private final String cls;
    private final Path path;
    private final BufferedWriter writer;

    public FileLogger(String cls, LoggerConfig loggerConfig) {
        this.cls = cls;
        this.path = Paths.get(loggerConfig.getPath());
        this.writer = initFileWriter();
    }

    private BufferedWriter initFileWriter() {
        String filePath = path + "/logs.log";
        if (Files.exists(Path.of(filePath))) {
            try {
                return new BufferedWriter(
                        new FileWriter(filePath, true)
                );
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to initialize FileLogger", e);
            }
        }

        try {
            return new BufferedWriter(
                    new FileWriter(path.resolve("logs.log").toFile(), true)
            );
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to initialize FileLogger", e);
        }
    }

    private synchronized void write(Log log) {
        String formatted = log.formatLog();
        try {
            writer.write(formatted);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write log entry", e);
        }
    }

    private void write(Log log, Throwable trace) {
        try {
            writer.write(log.formatLog());
            writer.newLine();

            PrintWriter pw = new PrintWriter(writer);
            trace.printStackTrace(pw);

            writer.flush();
        } catch (IOException e) {
            System.out.println("Error writing exception:" + e.getMessage());
        }
    }

    @Override
    public void info(String message) {
        Log log = new Log(cls, LogLevel.INFO, null, message);
        write(log);
    }

    @Override
    public void warn(String message) {
        Log log = new Log(cls, LogLevel.WARN, null, message);
        write(log);
    }

    @Override
    public void error(String message, Throwable trace) {
        Log log = new Log(cls, LogLevel.ERROR, null, message);
        write(log, trace);
    }

    @Override
    public void debug(String message) {
        Log log = new Log(cls, LogLevel.DEBUG, null, message);
        write(log);
    }
}
