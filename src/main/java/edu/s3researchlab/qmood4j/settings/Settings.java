package edu.s3researchlab.qmood4j.settings;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import edu.s3researchlab.qmood4j.utils.FileUtils;
import edu.s3researchlab.qmood4j.utils.LoggerUtils;
import picocli.CommandLine.IVersionProvider;

public class Settings implements IVersionProvider {

    /** The project's current folder */
    public static Path folder;

    public static String outFolderName = "out";

    public static String ignoreFileName = "ignore.txt";

    public static String outputErrorsFileName = "errors.txt";

    public static String metricsOverviewFileName = "metrics-overview.txt";

    public static String outputDetailedFileName = "metrics-detailed.txt";

    public String[] getVersion() {

        Package p = getClass().getPackage();
        String version = p.getImplementationVersion();

        return new String[] { version };
    }

    private static Path getWorkingPath() {

        return Paths.get("").toAbsolutePath();
    }

    public static Path getOutFolder() {

        return getWorkingPath().resolve(outFolderName);
    }

    public static Path getOutIgnoreFile() {

        return getOutFolder().resolve(Settings.ignoreFileName);
    }

    public static Path getProjectFolder() {

        return getOutFolder().resolve(folder.getFileName().toString());
    }

    public static Path getProjectDependenciesFolder() {

        return getProjectFolder().resolve("dependencies");
    }

    public static Path getProjectMetricsOverviewFile() {

        return getProjectFolder().resolve(metricsOverviewFileName);
    }

    public static Path getProjectIgnoreFile() {

        return getProjectFolder().resolve(Settings.ignoreFileName);
    }

    public static void init(Path folder) {

        Settings.folder = folder;
        
        Path outIgnoreFile = Settings.getOutIgnoreFile();
        Path projectFolder = Settings.getProjectFolder();
        Path projectIgnoreFile = Settings.getProjectIgnoreFile();

        FileUtils.createFolderIfNotExists(projectFolder);

        if (!Files.exists(outIgnoreFile)) {
            FileUtils.write(outIgnoreFile, String.join("\n", getDefaultIgnore()));
        }

        if (!Files.exists(projectIgnoreFile)) {
            FileUtils.write(projectIgnoreFile, String.join("\n", ""));
        }
        
        LoggerUtils.setAppenders();
    }

    private static List<String> getDefaultIgnore() {

        return List.of(".*module-info.java", ".*package-info.java", ".*/target/.*", ".*/test/.*");
    }

}
