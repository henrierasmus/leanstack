package com.henrierasmus.leanstack.logger;

public class LoggerConfig {
    LoggerType type;
    String path;

    public LoggerConfig(LoggerType type, String path) {
        this.type = type;
        this.path = path;
    }

    public LoggerConfig(LoggerType type) {
        this.type = type;
    }

    public LoggerType getType() {
        return type;
    }

    public void setType(LoggerType type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
