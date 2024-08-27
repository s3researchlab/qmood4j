package edu.s3rl.qmood4j.utils;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;

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

    public static void createEmptyFileIfNotExists(Path file) {

        checkNotNull(file);

        try {
            if (!Files.exists(file)) {
                file.toFile().createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path createFolderIfNotExists(Path folder) {

        checkNotNull(folder);

        try {
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }
        } catch (IOException e) {
            new RuntimeException(e);
        }

        return folder;
    }

    public static void deleteFolderRecursively(Path folder) {

        if (!Files.exists(folder)) {
            return;
        }

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

    public static String readContent(Path file) {

        try {
            return Files.readString(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public static Path getCurrentFolder() {

        return Paths.get(System.getProperty("user.dir"));
    }

    public static Path getUserFolder() {

        return Paths.get(System.getProperty("user.home")).resolve(".qmood4j");
    }

    public static Path getCacheFolder() {

        Path cacheFolder = getUserFolder().resolve("cache");

        return cacheFolder;
//        return createFolderIfNotExists(cacheFolder);
    }

    public static String checksum(Path file) {

        try {

            ByteSource byteSource = com.google.common.io.Files.asByteSource(file.toFile());

            HashCode hc = byteSource.hash(Hashing.sha256());

            return hc.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeAsAppend(Path outputFile, String content) {

        checkNotNull(outputFile);

        if (content == null) {
            content = "";
        }

        createEmptyFileIfNotExists(outputFile);

        try {

            Files.writeString(outputFile, content, StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
