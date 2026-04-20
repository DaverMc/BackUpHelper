package de.daver.backup;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoggingHelper {

    static final Logger LOGGER = Logger.getLogger("BackUp-Helper");
    static final AtomicBoolean debugging = new AtomicBoolean(false);

    public static void info(String message, Object... values) {
        log(Level.INFO, message, values);
    }

    public static void debug(String message, Object... values) {
        if(!debugging.get()) return;
        log(Level.FINE, message, values);
    }

    public static void error(Throwable throwable) {
        StringBuilder errorMessage = new StringBuilder(throwable.getClass().getName());
        errorMessage.append(" (\"").append(throwable.getMessage()).append("\")");
        for (var element : throwable.getStackTrace()) {
            errorMessage.append("\n\t")
                    .append(element.toString());
        }
        print(Level.SEVERE, errorMessage.toString());
    }

    private static void print(Level level, String message) {
        //LOGGER.log(level, message);
        IO.println("[" + level.getName() + "]: " + message);
    }

    static void log(Level level, String formatted, Object... values) {
        print(level, String.format(formatted, values));
    }

}
