package edu.s3.qmood4j.parser;

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

    private List<String> ignored = new ArrayList<>();

    private List<Path> javaFiles = new ArrayList<>();

    private List<Path> dependencyFiles = new ArrayList<>();

    public CodeLoader(Path folder) {

        this.folder = folder;

        this.addIgnored(".*module-info.java");
        this.addIgnored(".*package-info.java");
        this.addIgnored(".*/target/?.*");
        this.addIgnored(".*/src/test/java/.*");
        this.addIgnored(".*Test.java");
    }

    public void setAlwaysDownload(boolean alwaysDownload) {
        this.alwaysDownload = alwaysDownload;
    }

    public void addIgnored(String pattern) {

        this.ignored.add(pattern);
    }

    public List<Path> getJavaFiles() {

        return this.javaFiles;
    }

    public List<Path> getDependencyFiles() {

        return this.dependencyFiles;
    }

    public void load() {

        logger.info(LoggerUtils.separator);
        logger.info(LoggerUtils.green("Code Loader"));
        logger.info(LoggerUtils.separator);
        logger.info("");
        logger.info("Ignoring the following patterns");
        logger.info("");

        for (int i = 0; i < ignored.size(); i++) {

            String pattern = ignored.get(i);

            logger.info("({}/{}) Pattern ignored: {}", i + 1, ignored.size(), pattern);
        }

        logger.info("");
        logger.info("Completed ");

        this.downloadMavenDependencies();
        this.loadDependencyFiles();
        this.loadJavaFiles();
    }

    private void downloadMavenDependencies() {

        List<Path> pomFiles = FileUtils.getFilesFromFolder(folder, ignored, "pom.xml");

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

        logger.info("");
        logger.info(LoggerUtils.title("Loading .java files"));

        this.javaFiles.addAll(FileUtils.getFilesFromFolder(this.folder, this.ignored, ".java"));

        logger.info("");
        logger.info("Completed");
        logger.info("");
        logger.info("Java files found: {}", this.javaFiles.size());
    }

    private void loadDependencyFiles() {

        logger.info("");
        logger.info(LoggerUtils.title("Loading .jar dependency files"));

        this.dependencyFiles.addAll(FileUtils.getFilesFromFolder(this.folder, ignored, ".jar"));

        logger.info("");
        logger.info("Completed");
        logger.info("");
        logger.info("Dependencies found: {}", this.dependencyFiles.size());
    }

}
