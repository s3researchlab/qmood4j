package edu.s3.qmood4j.settings;

import picocli.CommandLine.IVersionProvider;

public class Settings implements IVersionProvider {

    public static String tempFolderName = ".qmood4j";

    public static String ignoreFileName = ".ignore";

    public String[] getVersion() {

        Package p = getClass().getPackage();
        String version = p.getImplementationVersion();

        return new String[] { version };
    }
}
