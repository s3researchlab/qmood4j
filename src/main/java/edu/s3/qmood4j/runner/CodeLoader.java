package edu.s3.qmood4j.runner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.model.Model;

import edu.s3.qmood4j.utils.FileUtils;
import edu.s3.qmood4j.utils.LoggerUtils;
import edu.s3.qmood4j.utils.MavenUtils;

public class CodeLoader {

    private static Logger logger = LogManager.getLogger(CodeLoader.class);

    private Path folder;

    private boolean alwaysDownload = false;

    private List<String> ignore = new ArrayList<>();

    private List<Path> javaFiles = new ArrayList<>();

    private List<Path> dependencyFiles = new ArrayList<>();

    public CodeLoader(Path folder) {

        this.folder = folder;

        this.addIgnore(".*module-info.java");
        this.addIgnore(".*package-info.java");
        this.addIgnore(".*/target/?.*");
        this.addIgnore(".*/src/test/java/.*");
        this.addIgnore(".*Test.java");
    }

    public void setAlwaysDownload(boolean alwaysDownload) {
        this.alwaysDownload = alwaysDownload;
    }

    public void addIgnore(String pattern) {

        if (pattern == null || pattern.trim().isEmpty()) {
            return;
        }
        
        this.ignore.add(pattern);
    }

    public List<Path> getJavaFiles() {

        return this.javaFiles;
    }

    public List<Path> getDependencyFiles() {

        return this.dependencyFiles;
    }

    public void load() {

        logger.debug(LoggerUtils.separator);
        logger.debug(LoggerUtils.green("Code Loader"));
        logger.debug(LoggerUtils.separator);
        logger.debug("");
        logger.debug("Ignoring the following patterns");
        logger.debug("");

        for (int i = 0; i < ignore.size(); i++) {

            String pattern = ignore.get(i);

            logger.debug("({}/{}) Pattern ignored: {}", i + 1, ignore.size(), pattern);
        }

        logger.debug("");
        logger.debug("Completed ");

        this.downloadMavenDependencies();
        this.loadDependencyFiles();
        this.loadJavaFiles();
    }

    private void downloadMavenDependencies() {

        List<Path> pomFiles = FileUtils.getFilesFromFolder(folder, ignore, "/pom.xml");

        for (Path pomFile : pomFiles) {

            Model model = MavenUtils.readPomFile(pomFile);

            Path folder = pomFile.getParent();
            Path jarsFolder = folder.resolve(".qmood4j", "dependencies");

            if (Files.exists(folder.resolve("src", "main", "java"))) {
                dependencyFiles.add(folder.resolve("src", "main", "java"));
            } else if (Files.exists(folder.resolve("src", "main"))) {
                dependencyFiles.add(folder.resolve("src", "main"));
            } else if (Files.exists(folder.resolve("src"))) {
                dependencyFiles.add(folder.resolve("src"));
            }

            if (alwaysDownload) {
                FileUtils.deleteFolderRecursively(jarsFolder);
            }

            MavenUtils.downloadDependencies(model, jarsFolder);
        }
    }

    private void loadJavaFiles() {

        logger.debug("");
        logger.debug(LoggerUtils.title("Loading .java files"));

        this.javaFiles.addAll(FileUtils.getFilesFromFolder(this.folder, this.ignore, ".java"));

        logger.debug("");
        logger.debug("Completed");
        logger.debug("");
        logger.debug("Java files found: {}", this.javaFiles.size());
    }

    private void loadDependencyFiles() {

        logger.debug("");
        logger.debug(LoggerUtils.title("Loading .jar dependency files"));

        this.dependencyFiles.addAll(FileUtils.getFilesFromFolder(this.folder, ignore, ".jar"));

        logger.debug("");
        logger.debug("Completed");
        logger.debug("");
        logger.debug("Dependencies found: {}", this.dependencyFiles.size());
    }

}
