package com.henrierasmus.leanstack.logger.internal;

import com.henrierasmus.leanstack.logger.LogLevel;

import java.time.LocalDateTime;

public class Log {
    private String cls;
    private LocalDateTime logTime;
    private LogLevel level;
    private String event;
    private String message;

    public Log(String cls, LogLevel level, String event, String message) {
        this.cls = cls;
        this.logTime = LocalDateTime.now();
        this.level = level;
        this.event = event;
        this.message = message;
    }

    public String formatLog() {
        return String.format("%s[%s]: %s", logTime.toString(), level, message);
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
