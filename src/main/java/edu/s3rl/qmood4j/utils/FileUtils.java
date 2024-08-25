package edu.s3rl.qmood4j.utils;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
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
            return Files.walk(folder).filter(Files::isDirectory).filter(s -> !matches(s, ignoredPatterns))
                    .collect(Collectors.toList());
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

    private static void createEmptyFileIfNotExists(Path file) {

        checkNotNull(file);

        try {
            if (!Files.exists(file)) {
                file.toFile().createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createFolderIfNotExists(Path folder) {

        checkNotNull(folder);

        try {
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }
        } catch (IOException e) {
            new RuntimeException(e);
        }
    }

    public static void deleteFolderRecursively(Path folder) {

        try {

            Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readLines(Path file) {

        try {
            return Files.readAllLines(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path getCurrentFolder() {

        return Paths.get(System.getProperty("user.dir"));
    }

    public static List<String> readIgnoreFile(Path ignoreFile) {

        if (!Files.exists(ignoreFile)) {
            return List.of();
        }

        return readLines(ignoreFile);
    }

    public static void write(Path outputFile, String content) {

        checkNotNull(outputFile);

        if (content == null) {
            content = "";
        }

        createEmptyFileIfNotExists(outputFile);

        try {

            Files.writeString(outputFile, content);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
