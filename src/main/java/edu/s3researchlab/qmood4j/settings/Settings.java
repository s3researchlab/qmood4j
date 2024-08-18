package edu.s3researchlab.qmood4j.settings;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import edu.s3researchlab.qmood4j.utils.FileUtils;
import edu.s3researchlab.qmood4j.utils.LoggerUtils;
import picocli.CommandLine.IVersionProvider;

public class Settings implements IVersionProvider {

    /** The project's current folder */
    public static Path folder;

    public static String outFolderName = ".qmood4j";

    public static String ignoreFileName = "ignore.txt";

    public static String logFileName = "out.log";

    public static String metricsOverviewFileName = "metrics-overview.txt";

    public String[] getVersion() {

        Package p = getClass().getPackage();
        String version = p.getImplementationVersion();

        return new String[] { version };
    }

    public static Path getOutFolder() {

        return folder.resolve(outFolderName);
    }
    
    public static Path getDependenciesFolder() {

        return folder.resolve(".qmood4j", "dependencies");
    }

    public static Path getIgnoreFile() {

        return getOutFolder().resolve(Settings.ignoreFileName);
    }
    
    public static Path getMetricsOverviewFile() {

        return getOutFolder().resolve(Settings.metricsOverviewFileName);
    }

    public static void init(Path folder) {

        Settings.folder = folder;
        
        FileUtils.createFolderIfNotExists(getOutFolder());
       
        Path ignoreFile = Settings.getIgnoreFile();

        if (!Files.exists(ignoreFile)) {
            FileUtils.write(ignoreFile, String.join("\n", getDefaultIgnore()));
        }

        LoggerUtils.setAppenders();
    }

    private static List<String> getDefaultIgnore() {

        return List.of(".*module-info.java", ".*package-info.java", ".*/target/.*", ".*/test/.*");
    }

}
