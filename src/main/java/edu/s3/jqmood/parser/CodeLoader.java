package edu.s3.jqmood.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.s3.jqmood.utils.FileUtils;

public class CodeLoader {

    private static Logger logger = LogManager.getLogger(CodeLoader.class);

    private List<String> ignoredPatterns = new ArrayList<>();

    public CodeLoader() {
        this.addIgnoredPattern(".*module-info.java");
        this.addIgnoredPattern(".*package-info.java");
        this.addIgnoredPattern(".*/src/test/java/.*");
        this.addIgnoredPattern(".*Test.java");
    }

    public void addIgnoredPattern(String pattern) {
        this.ignoredPatterns.add(pattern);
    }

    public List<Path> loadFile(Path folder) throws IOException {

        logger.info("Folder: {}", folder);

        return FileUtils.getFilesFromFolder(folder, ignoredPatterns, ".java");
    }
}
