package edu.s3.jqmood.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {

    /**
     * Private Constructor will prevent the instantiation of this class directly
     */
    private FileUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static List<Path> getFilesFromFolder(Path folder, String extension) throws IOException {

        return getFilesFromFolder(folder, new ArrayList<>(), extension);
    }

    public static List<Path> getFilesFromFolder(Path folder, List<String> ignoredPatterns, String extension)
            throws IOException {

        return Files.walk(folder)
                .filter(Files::isRegularFile)
                .filter(s -> s.toString().endsWith(extension))
                .filter(s -> !matches(s, ignoredPatterns))
                .collect(Collectors.toList());
    }

    /**
     * Verify if a given path (converted to a full path format) matches some pattern
     * provided as input
     * 
     * @param path     should not be null
     * @param patterns should not be null
     * 
     * @return True if the file matches some pattern. Otherwise, False if there is
     *         not match
     */
    public static boolean matches(Path path, List<String> patterns) {

        for (String pattern : patterns) {

            if (path.toString().matches(pattern)) {
                return true;
            }
        }

        return false;
    }

}
