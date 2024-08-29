package edu.s3rl.qmood4j.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;

import edu.s3rl.qmood4j.runner.CodeParser;

public class LoggerUtils {

    private static Logger logger = LogManager.getLogger(CodeParser.class);

    public static String separator = "------------------------------------------------------------------------";

    /**
     * Private Constructor will prevent the instantiation of this class directly
     */
    private LoggerUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static void section(String sectionName, Object... params) {
        logger.info(LoggerUtils.separator);
        logger.info(sectionName, params);
        logger.info(LoggerUtils.separator);
    }

    public static void setEnabled(boolean enabled) {

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);

        if (enabled) {
            Configurator.setRootLevel(Level.INFO);
        } else {
            Configurator.setRootLevel(Level.OFF);
        }

        ctx.updateLoggers();
    }
}
