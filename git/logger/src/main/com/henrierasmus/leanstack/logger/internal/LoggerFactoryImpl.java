package com.henrierasmus.leanstack.logger.internal;

import com.henrierasmus.leanstack.logger.LoggerConfig;
import com.henrierasmus.leanstack.logger.Logger;
import com.henrierasmus.leanstack.logger.LoggerFactory;
import com.henrierasmus.leanstack.logger.LoggerType;

public class LoggerFactoryImpl implements LoggerFactory {

    @Override
    public Logger getLogger(String cls, LoggerConfig loggerConfig) {

        if (loggerConfig.getPath() == null) {
            throw new IllegalArgumentException("'path' not provided");
        }

        switch(loggerConfig.getType()) {
            case LoggerType.FILE: return new FileLogger(cls, loggerConfig);
            default: return null;
        }
    }
}
