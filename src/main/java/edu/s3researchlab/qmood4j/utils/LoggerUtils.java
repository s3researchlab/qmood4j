package edu.s3researchlab.qmood4j.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;

import edu.s3researchlab.qmood4j.runner.CodeParser;
import edu.s3researchlab.qmood4j.settings.Settings;

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

    public static void setAppenders() {

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();

        FileAppender errorFA = FileAppender.newBuilder().setName("logToFile").withAppend(false)
                .withFileName(Settings.getOutFolder().resolve("info.log").toString())
                .setLayout(
                        PatternLayout.newBuilder().withPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5level] %msg%n").build())
                .setConfiguration(config).build();

        errorFA.start();

        config.addAppender(errorFA);
        ctx.getRootLogger().addAppender(config.getAppender(errorFA.getName()));
        ctx.updateLoggers();
    }
}
