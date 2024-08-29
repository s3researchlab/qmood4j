package edu.s3rl.qmood4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.s3rl.qmood4j.runner.CodeCalculator;
import edu.s3rl.qmood4j.runner.CodeLoader;
import edu.s3rl.qmood4j.runner.CodeParser;
import edu.s3rl.qmood4j.settings.Settings;
import edu.s3rl.qmood4j.utils.LoggerUtils;
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

    @Option(names = { "--help" }, usageHelp = true, description = "display the help menu")
    private boolean helpRequested = false;

    @Option(names = { "--version" }, versionHelp = true, description = "print version information and exit")
    private boolean versionRequested = false;
    
    @Option(names = { "-e", "--exclude" }, split = ",", description = "the list of folders/files to exclude")
    private List<String> exclude = Settings.getDefaultExclude();

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

        Settings.init();

        LoggerUtils.section("Scanning folder: " + folder);

        CodeLoader loader = new CodeLoader(folder, exclude);

        loader.load();

        CodeParser parser = new CodeParser(loader);

        parser.parse();

        CodeCalculator calculator = new CodeCalculator(parser, folder);

        calculator.calculate();

        LoggerUtils.section("SUCCESS");
        logger.info("Total time: {} s", Settings.getEstimatedTimeInSeconds());
        logger.info("Finished at: {}", Settings.getDateTimeNow());
        logger.info(LoggerUtils.separator);

        return 0;
    }

}
