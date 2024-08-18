package edu.s3researchlab.qmood4j.settings;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import edu.s3researchlab.qmood4j.utils.FileUtils;
import edu.s3researchlab.qmood4j.utils.LoggerUtils;
import picocli.CommandLine.IVersionProvider;

public class Settings implements IVersionProvider {

    /** The project's current folder */
    public static Path folder;

    public static long startTime = 0L;

    public static String outFolderName = ".qmood4j";
    
    public static String dependenciesFolderName = "dependencies";

    public static String ignoreFileName = "ignore.txt";

    public static String logFileName = "out.log";

    public static String metricsOverviewFileName = "metrics-overview.txt";
    
    public static String metricsDetailedFileName = "metrics-detailed.txt";

    public String[] getVersion() {

        Package p = getClass().getPackage();
        String version = p.getImplementationVersion();

        return new String[] { version };
    }

    public static Path getOutFolder() {

        return folder.resolve(outFolderName);
    }

    public static Path getIgnoreFile() {

        return getOutFolder().resolve(Settings.ignoreFileName);
    }

    public static Path getMetricsOverviewFile() {

        return getOutFolder().resolve(Settings.metricsOverviewFileName);
    }
    
    public static Path getMetricsDetailedFile() {
        
        return getOutFolder().resolve(Settings.metricsDetailedFileName);
    }

    public static void init(Path folder) {

        Settings.folder = folder;

        Settings.startTime = System.currentTimeMillis();

        FileUtils.createFolderIfNotExists(getOutFolder());

        Path ignoreFile = Settings.getIgnoreFile();

        if (!Files.exists(ignoreFile)) {
            FileUtils.write(ignoreFile, String.join("\n", getDefaultIgnore()));
        }

        LoggerUtils.setAppenders();
    }

    public static double getEstimatedTimeInSeconds() {
        
        return (System.currentTimeMillis() - startTime) / 1000.0;
    }

    public static String getDateTimeNow() {

        ZonedDateTime dateTimeNow = ZonedDateTime.now();

        return DateTimeFormatter.RFC_1123_DATE_TIME.format(dateTimeNow);
    }

    private static List<String> getDefaultIgnore() {

        return List.of(".*module-info.java", ".*package-info.java", ".*/target/.*", ".*/test/.*");
    }

    

}
