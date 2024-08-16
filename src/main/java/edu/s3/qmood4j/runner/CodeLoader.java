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

        if (pattern.isBlank()) {
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

        LoggerUtils.section("Ignoring the following patterns");

        for (int i = 0; i < ignore.size(); i++) {

            String pattern = ignore.get(i);

            logger.debug("({}/{}) Pattern ignored: {}", i + 1, ignore.size(), pattern);
        }

        this.downloadMavenDependencies();
        this.loadDependencyFiles();
        this.loadJavaFiles();
    }

    private void downloadMavenDependencies() {

        LoggerUtils.section("Processing Maven dependencies");

        List<Path> pomFiles = getFilesFromFolder(folder, "/pom.xml");

        for (int i = 0; i < pomFiles.size(); i++) {

            Path pomFile = pomFiles.get(i);
            
            logger.debug("({}/{}) Pom file: {}", i + 1, pomFiles.size(), pomFile);
            
            Model model = MavenUtils.readPomFile(pomFile);

            Path pomFolder = pomFile.getParent();
            Path jarsFolder = folder.resolve(".qmood4j", "dependencies");

            if (Files.exists(pomFolder.resolve("src", "main", "java"))) {
                dependencyFiles.add(pomFolder.resolve("src", "main", "java"));
            } else if (Files.exists(pomFolder.resolve("src", "main"))) {
                dependencyFiles.add(pomFolder.resolve("src", "main"));
            } else if (Files.exists(pomFolder.resolve("src"))) {
                dependencyFiles.add(pomFolder.resolve("src"));
            }

            if (alwaysDownload) {
                FileUtils.deleteFolderRecursively(jarsFolder);
            }

            MavenUtils.downloadDependencies(model, jarsFolder);
        }
    }

    private void loadJavaFiles() {

        LoggerUtils.section("Loading .java files");

        this.javaFiles.addAll(getFilesFromFolder(this.folder, ".java"));

        logger.debug("Completed. Java files found: {}", this.javaFiles.size());
    }

    private void loadDependencyFiles() {

        LoggerUtils.section("Loading .jar dependency files");

        this.dependencyFiles.addAll(getFilesFromFolder(this.folder, ".jar"));

        logger.debug("Completed. Dependencies found: {}", this.dependencyFiles.size());
    }

    private List<Path> getFilesFromFolder(Path folder, String fileExtension) {

        return FileUtils.getFilesFromFolder(folder, this.ignore, fileExtension);
    }

}
