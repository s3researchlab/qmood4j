package edu.s3.jqmood.utils;

import static com.google.common.base.Preconditions.checkArgument;

import java.nio.file.Files;
import java.nio.file.Path;

public class ManagementToolUtils {

    public static enum MANAGEMENT_TOOLS {
        MAVEN, GRADLE, ECLIPSE, OTHER
    }

    /**
     * Private Constructor will prevent the instantiation of this class directly
     */
    private ManagementToolUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static MANAGEMENT_TOOLS getManagementTool(Path folder) {

        checkArgument(Files.exists(folder), folder + " not found");

        if (Files.exists(folder.resolve("pom.xml"))) {
            return MANAGEMENT_TOOLS.MAVEN;
        }

        return MANAGEMENT_TOOLS.OTHER;
    }
}
