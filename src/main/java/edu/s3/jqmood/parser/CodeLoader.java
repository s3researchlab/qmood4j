package edu.s3.jqmood.parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.s3.jqmood.loader.MavenLoader;
import edu.s3.jqmood.utils.FileUtils;

public class CodeLoader {

    private static Logger logger = LogManager.getLogger(CodeLoader.class);

    private Path folder;

    private List<String> ignoredPatterns = new ArrayList<>();

    private List<Path> jarFiles = new ArrayList<>();

    private List<Path> dependencyFiles = new ArrayList<>();

    public CodeLoader(Path folder) {

        this.folder = folder;

        this.addIgnoredPattern(".*module-info.java");
        this.addIgnoredPattern(".*package-info.java");
        this.addIgnoredPattern(".*/src/test/java/.*");
        this.addIgnoredPattern(".*Test.java");
    }

    public void addIgnoredPattern(String pattern) {

        this.ignoredPatterns.add(pattern);
    }

    public List<Path> getJavaFiles() {

        return this.jarFiles;
    }

    public List<Path> getDependencyFiles() {

        return this.dependencyFiles;
    }

    public void load() {
        this.loadJarFiles();
        this.loadDependencyFiles();
    }

    private void loadJarFiles() {

        this.jarFiles = FileUtils.getFilesFromFolder(this.folder, this.ignoredPatterns, ".java");
    }

    private void loadDependencyFiles() {

        if (Files.exists(this.folder.resolve("pom.xml"))) {
            this.dependencyFiles = new MavenLoader().load(this.folder);
        }
    }

}
