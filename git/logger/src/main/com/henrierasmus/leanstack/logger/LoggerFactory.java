package com.henrierasmus.leanstack.logger;

public interface LoggerFactory {
    Logger getLogger(String cls, LoggerConfig loggerConfig);
}
