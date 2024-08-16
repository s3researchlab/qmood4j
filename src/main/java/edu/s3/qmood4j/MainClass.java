package edu.s3.qmood4j;

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
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

@Command(sortOptions = false, versionProvider = Settings.class)
public class MainClass implements Callable<Integer> {

    private static Logger logger = LogManager.getLogger(MainClass.class);

    @Spec
    CommandSpec spec;

    private Path folder;

    @Option(names = { "-o",
            "--output" }, description = "the output file with qmood metrics (default: /{folder}/qmood.properties")
    private Path outputFile = null;

    @Option(names = { "-i", "--ignore-file" }, description = "the path to the ignore file")
    private Path ignoreFile = null;
    
    @Option(names = { "-a",
            "--always-download" }, description = "always download all dependencies (default: ${DEFAULT-VALUE})")
    private boolean alwaysDownload = false;

    @Option(names = { "--help" }, usageHelp = true, description = "display the help menu")
    private boolean helpRequested = false;

    @Option(names = { "--version" }, versionHelp = true, description = "print version information and exit")
    private boolean versionRequested = false;

    @Option(names = { "--verbose" }, description = "enable debugging mode")
    public void setVerbose(boolean[] verbose) {

        if (verbose.length >= 1) {
            LoggerUtils.setLevel(Level.DEBUG);
        }
    }

    @Parameters(paramLabel = "folder", description = "the folder with the source code")
    public void setFolder(Path folder) {

        if (!Files.exists(folder)) {
            throw new ParameterException(spec.commandLine(), "%s does not exists".formatted(folder));
        }

        if (!Files.isDirectory(folder)) {
            throw new ParameterException(spec.commandLine(), "%s is not a folder".formatted(folder));
        }

        this.folder = folder;
    }

    public static void main(String[] args) {

        CommandLine commandLine = new CommandLine(new MainClass());

        commandLine.setCaseInsensitiveEnumValuesAllowed(true);

        int exitCode = commandLine.execute(args);

        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {

        Settings.folder = folder; 
        
        init();

        LoggerUtils.section("Scanning folder: " + folder);

        logger.debug("Ignore File: {}", ignoreFile);

        CodeLoader loader = new CodeLoader(folder);

        loader.setAlwaysDownload(alwaysDownload);

        for (String ignore : FileUtils.readIgnoreFile(ignoreFile)) {
            loader.addIgnore(ignore);
        }

        loader.load();

        CodeParser parser = new CodeParser(loader);

        parser.parse();

        CodeCalculator calculator = new CodeCalculator(parser, outputFile);

        calculator.calculate();

        return 0;
    }

    private void init() {

        Path tempFolder = folder.resolve(Settings.tempFolderName);

        if (!Files.exists(tempFolder)) {
            FileUtils.createFolder(tempFolder);
        }

        if (ignoreFile == null) {
            ignoreFile = tempFolder.resolve(Settings.ignoreFileName);
        }
        
        if(outputFile == null) {
            outputFile = Settings.getDefaultOutputFile();
        }

        if (!Files.exists(ignoreFile)) {
            FileUtils.write(ignoreFile, "");
        }
    }

}
