package edu.s3.qmood4j.settings;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import edu.s3.qmood4j.utils.FileUtils;
import picocli.CommandLine.IVersionProvider;

public class Settings implements IVersionProvider {

    /** The project's current folder */
    public static Path folder;

    public static String tempFolderName = ".qmood4j";

    public static String ignoreFileName = ".ignore";

    public static String outputFileName = "qmood4j.properties";

    public String[] getVersion() {

        Package p = getClass().getPackage();
        String version = p.getImplementationVersion();

        return new String[] { version };
    }

    public static Path getDefaultOutputFile() {
        return folder.resolve(outputFileName);
    }

    public static Path getTempFolder() {
        return folder.resolve(tempFolderName);
    }

    public static Path getIgnoreFile() {
        return getTempFolder().resolve(ignoreFileName);
    }

    public static void init() {

        Path tempFolder = folder.resolve(Settings.tempFolderName);

        if (!Files.exists(tempFolder)) {
            FileUtils.createFolder(tempFolder);
        }

        Path ignoreFile = getTempFolder().resolve(ignoreFileName);

        if (!Files.exists(ignoreFile)) {
            FileUtils.write(ignoreFile, String.join("\n", getDefaultIgnore()));
        }
    }

    private static List<String> getDefaultIgnore() {

        List<String> ignores = new ArrayList<>();

        ignores.add(".*module-info.java");
        ignores.add(".*package-info.java");
        ignores.add(".*/target/.*");
        ignores.add(".*/test/.*");

        return ignores;
    }
}
