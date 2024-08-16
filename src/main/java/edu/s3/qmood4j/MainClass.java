package edu.s3.qmood4j;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

@Command(sortOptions = false, versionProvider = Settings.class)
public class MainClass implements Callable<Integer> {

    private static Logger logger = LogManager.getLogger(MainClass.class);

    @NotNull(message = "folder cannot be null")
    @Parameters(paramLabel = "folder", description = "the folder with the source code")
    protected Path folder;

    @Option(names = { "-o",
            "--output" }, description = "the output file with qmood metrics (default: ${DEFAULT-VALUE})")
    protected Path outputFile = FileUtils.getCurrentFolder().resolve("qmood.properties");

    @Option(names = { "-a",
            "--always-download" }, description = "always download all dependencies (default: ${DEFAULT-VALUE})")
    protected boolean alwaysDownload = false;

    @Option(names = { "-i", "--ignore-file" }, description = "the path to the ignore file")
    protected Path ignoreFile = null;

    @Option(names = { "--help" }, usageHelp = true, description = "display the help menu")
    protected boolean helpRequested = false;

    @Option(names = { "--version" }, versionHelp = true, description = "print version information and exit")
    protected boolean versionRequested = false;

    @Option(names = { "--verbose" }, description = "enable the debugging mode")
    public void setVerbose(boolean[] verbose) {

        if (verbose.length >= 1) {
            LoggerUtils.setLevel(Level.DEBUG);
        } 
    }

    public static void main(String[] args) {

        CommandLine commandLine = new CommandLine(new MainClass());

        commandLine.setUsageHelpWidth(150);
        commandLine.setCaseInsensitiveEnumValuesAllowed(true);

        int exitCode = commandLine.execute(args);

        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {

        checkNotNull(folder, "folder should not be null");
        checkArgument(Files.exists(folder), "folder should exists");

        init();

        LoggerUtils.section("Scanning folder: {}", folder);
        
        logger.debug("Ignore File: {}", ignoreFile);

        CodeLoader loader = new CodeLoader(folder);

        loader.setAlwaysDownload(alwaysDownload);

        for (String ignore : FileUtils.readIgnoreFile(ignoreFile)) {
            loader.addIgnore(ignore);
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

        logger.debug(LoggerUtils.separator);
        logger.debug("SUCCESS");
        logger.debug(LoggerUtils.separator);

        return null;
    }

    private void init() {

        Path tempFolder = folder.resolve(Settings.tempFolderName);

        if (!Files.exists(tempFolder)) {
            FileUtils.createFolder(tempFolder);
        }

        if (ignoreFile == null) {
            ignoreFile = tempFolder.resolve(Settings.ignoreFileName);
        }

        if (!Files.exists(ignoreFile)) {
            FileUtils.write(ignoreFile, "");
        }
    }

}
