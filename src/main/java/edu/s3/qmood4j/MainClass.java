package edu.s3.qmood4j;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import edu.s3.qmood4j.runner.CodeCalculator;
import edu.s3.qmood4j.runner.CodeLoader;
import edu.s3.qmood4j.runner.CodeParser;
import edu.s3.qmood4j.settings.Settings;
import edu.s3.qmood4j.utils.FileUtils;
import edu.s3.qmood4j.utils.LoggerUtils;
import jakarta.validation.constraints.NotNull;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(versionProvider = Settings.class)
public class MainClass implements Callable<Integer> {

    private static Logger logger = LogManager.getLogger(MainClass.class);

    @NotNull(message = "folder cannot be null")
    @Parameters(paramLabel = "folder", description = "the project's source code")
    protected Path folder;

    @Option(names = { "-o", "--output" }, description = "the output file with qmood metrics")
    protected Path outputFile = FileUtils.getCurrentFolder().resolve("qmood.properties");

    @Option(names = { "-d", "--debug" }, description = "enable the debugging mode")
    protected boolean debug = false;

    @Option(names = { "-a", "--always-download" }, description = "always download all dependencies")
    protected boolean alwaysDownload = false;

    @Option(names = { "-i", "--ignore-file" }, description = "the path to the ignore file")
    protected Path ignoreFile = FileUtils.getCurrentFolder().resolve(".qmood4jignore");

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display the help menu")
    protected boolean helpRequested = false;

    @Option(names = { "-V", "--version" }, versionHelp = true, description = "print version information and exit")
    protected boolean versionRequested = false;

    public static void main(String[] args) {

        CommandLine commandLine = new CommandLine(new MainClass());

        int exitCode = commandLine.setCaseInsensitiveEnumValuesAllowed(true).execute(args);

        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {

        checkNotNull(folder, "folder should not be null");
        checkArgument(Files.exists(folder), "folder should exists");

        if (debug) {
            Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.DEBUG);
        } else {
            Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.INFO);
        }

        logger.info("Scanning project...");
        logger.info("");
        logger.info("Folder: {}", folder);
        logger.info("");

        if (Files.exists(ignoreFile)) {
            logger.info("Ignore File: {}", ignoreFile);
            logger.info("");
        }

        CodeLoader loader = new CodeLoader(folder);

        loader.setAlwaysDownload(alwaysDownload);

        for (String ignore : FileUtils.readIgnoreFile(ignoreFile)) {
            loader.addIgnored(ignore);
        }

        loader.load();

        CodeParser parser = new CodeParser(loader.getJavaFiles());

        for (Path dependencyFile : loader.getDependencyFiles()) {
            parser.addLibraries(dependencyFile);
        }

        parser.parse();

        CodeCalculator calculator = new CodeCalculator(parser.getProjectModel());

        calculator.setOutputFile(outputFile);

        calculator.calculate();

        logger.info("");
        logger.info(LoggerUtils.separator);
        logger.info(LoggerUtils.green("SUCCESS"));
        logger.info(LoggerUtils.separator);

        return null;
    }

}
