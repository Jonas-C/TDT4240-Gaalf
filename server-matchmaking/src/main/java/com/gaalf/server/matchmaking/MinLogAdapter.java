package com.gaalf.server.matchmaking;

import com.esotericsoftware.minlog.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinLogAdapter extends Log.Logger {

    public static void registerSLF4J() {
        Log.setLogger(new MinLogAdapter());
    }

    @Override
    public void log(int level, String category, String message, Throwable ex) {
        Logger logger = LoggerFactory.getLogger("minlog." + category);
        switch (level) {
            case Log.LEVEL_ERROR:
                logger.error(message, ex);
                break;
            case Log.LEVEL_WARN:
                logger.warn(message, ex);
                break;
            case Log.LEVEL_INFO:
                logger.info(message, ex);
                break;
            case Log.LEVEL_DEBUG:
                logger.debug(message, ex);
                break;
            case Log.LEVEL_TRACE:
                logger.trace(message, ex);
                break;
        }
    }
}
