package edu.s3.qmood4j.settings;

import java.nio.file.Path;

import picocli.CommandLine.IVersionProvider;

public class Settings implements IVersionProvider {

    /** The project's current folder */
    public static Path folder;
    
    public static String tempFolderName = ".qmood4j";

    public static String ignoreFileName = ".ignore";

    public String[] getVersion() {

        Package p = getClass().getPackage();
        String version = p.getImplementationVersion();

        return new String[] { version };
    }
    
    public static Path getDefaultOutputFile() {
        return folder.resolve("qmood4j.properties");
    }
}
