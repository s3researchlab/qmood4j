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

    public static List<Path> getFilesFromFolder(Path folder, String extension) {

        return getFilesFromFolder(folder, new ArrayList<>(), extension);
    }

    public static List<Path> getFilesFromFolder(Path folder, List<String> ignoredPatterns, String extension) {

        if (!Files.exists(folder)) {
            return new ArrayList<>();
        }

        try {
            return Files.walk(folder).filter(Files::isRegularFile).filter(s -> s.toString().endsWith(extension))
                    .filter(s -> !matches(s, ignoredPatterns)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static List<Path> getSubFolders(Path folder, List<String> ignoredPatterns) {

        if (!Files.exists(folder)) {
            return new ArrayList<>();
        }

        try {
            return Files.walk(folder).filter(Files::isDirectory).filter(s -> !matches(s, ignoredPatterns)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public static String read(Path file) {
        try {
            return Files.readString(file);
        } catch (IOException e) {
            new RuntimeException(e);
        }
        return null;
    }

    public static void write(Path file, String content) {
        try {
            Files.writeString(file, content);
        } catch (IOException e) {
            new RuntimeException(e);
        }
    }

    public static void createIfNotExists(Path folder) {
        if (!Files.exists(folder)) {
            try {
                Files.createDirectories(folder);
            } catch (IOException e) {
                new RuntimeException(e);
            }
        }
    }

}
