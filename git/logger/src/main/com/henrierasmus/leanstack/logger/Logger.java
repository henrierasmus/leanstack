package com.henrierasmus.leanstack.logger;

public interface Logger {
    void info(String msg);
    void warn(String msg);
    void error(String msg, Throwable t);
    void debug(String msg);
}
