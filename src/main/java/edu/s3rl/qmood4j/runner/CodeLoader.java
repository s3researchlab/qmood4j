package edu.s3rl.qmood4j.runner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.apache.maven.model.Model;

import edu.s3rl.qmood4j.utils.FileUtils;
import edu.s3rl.qmood4j.utils.LoggerUtils;
import edu.s3rl.qmood4j.utils.MavenUtils;

public class CodeLoader {

    private static Logger logger = LogManager.getLogger(CodeLoader.class);

    private Path folder;

    private List<String> exclude = new ArrayList<>();

    private List<Path> javaFiles = new ArrayList<>();

    private List<Path> dependencyFiles = new ArrayList<>();

    public CodeLoader(Path folder, List<String> exclude) {

        this.folder = folder;

        logger.info("Ignoring the following patterns");

        for (int i = 0; i < exclude.size(); i++) {

            String item = exclude.get(i);

            if (Strings.isBlank(item)) {
                continue;
            }

            this.exclude.add(item);

            logger.info("({}/{}) Pattern ignored: {}", i + 1, exclude.size(), item);
        }
    }

    public List<Path> getJavaFiles() {

        return this.javaFiles;
    }

    public List<Path> getDependencyFiles() {

        return this.dependencyFiles;
    }

    public void load() {
        this.downloadMavenDependencies();
        this.downloadEclipseDependencies();
        this.loadJavaFiles();
    }

    private void downloadMavenDependencies() {

        LoggerUtils.section("Processing Maven dependencies");

        List<Path> pomFiles = getFilesFromFolder(folder, "/pom.xml");

        for (int i = 0; i < pomFiles.size(); i++) {

            Path pomFile = pomFiles.get(i);

            logger.info("({}/{}) Pom file: {}", i + 1, pomFiles.size(), pomFile);

            Model model = MavenUtils.readPomFile(pomFile);

            Path pomFolder = pomFile.getParent();

            if (Files.exists(pomFolder.resolve("src", "main", "java"))) {
                dependencyFiles.add(pomFolder.resolve("src", "main", "java"));
            } else if (Files.exists(pomFolder.resolve("src", "main"))) {
                dependencyFiles.add(pomFolder.resolve("src", "main"));
            } else if (Files.exists(pomFolder.resolve("src"))) {
                dependencyFiles.add(pomFolder.resolve("src"));
            }

            String checkSum = FileUtils.checksum(pomFile);

            Path jarsFolder = FileUtils.getCacheForPomFilesFolder(checkSum);
            
            if (!Files.exists(jarsFolder)) {

                FileUtils.createFolderIfNotExists(jarsFolder);

                MavenUtils.downloadDependencies(model, jarsFolder);
            }

            this.dependencyFiles.addAll(getFilesFromFolder(jarsFolder, ".jar"));                       
        }
    }

    private void downloadEclipseDependencies() {

        if (Files.exists(folder.resolve(".project")) && Files.exists(folder.resolve(".classpath"))) {

            if (Files.exists(folder.resolve("src"))) {
                
                this.dependencyFiles.add(folder.resolve("src"));
            }
        }
    }

    private void loadJavaFiles() {

        LoggerUtils.section("Loading .java files");

        this.javaFiles.addAll(getFilesFromFolder(this.folder, ".java"));

        logger.info("Completed. Java files found: {}", this.javaFiles.size());
    }

    private List<Path> getFilesFromFolder(Path folder, String fileExtension) {

        return FileUtils.getFilesFromFolder(folder, this.exclude, fileExtension);
    }

}
