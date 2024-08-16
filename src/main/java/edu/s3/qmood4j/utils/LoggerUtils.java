package edu.s3.qmood4j.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import edu.s3.qmood4j.runner.CodeParser;

public class LoggerUtils {

    private static Logger logger = LogManager.getLogger(CodeParser.class);

    public static final String RESET = "\u001B[0m";

    public static final String BLACK = "\u001B[30m";

    public static final String RED = "\u001B[31m";

    public static final String GREEN = "\u001B[32m";

    public static final String YELLOW = "\u001B[33m";

    public static final String BLUE = "\u001B[34m";

    public static final String PURPLE = "\u001B[35m";

    public static final String CYAN = "\u001B[36m";

    public static final String WHITE = "\u001B[37m";

    public static String separator = "------------------------------------------------------------------------";

    /**
     * Private Constructor will prevent the instantiation of this class directly
     */
    private LoggerUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static String color(String text, String color) {

        return "%s%s%s".formatted(color, text, RESET);
    }

    public static String green(String text) {

        return color(text, GREEN);
    }

    public static void section(String sectionName, Object... params) {
        logger.debug(LoggerUtils.separator);
        logger.debug("{}", green(sectionName), params);
        logger.debug(LoggerUtils.separator);
    }

    public static void setLevel(Level level) {

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig log = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

        log.setLevel(level);

        ctx.updateLoggers();
    }
}
