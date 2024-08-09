package edu.s3.jqmood.parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.s3.jqmood.loader.MavenLoader;
import edu.s3.jqmood.utils.FileUtils;
import edu.s3.jqmood.utils.LoggerUtils;

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

		logger.info(LoggerUtils.separator);
		logger.info("Code Loader");
		logger.info(LoggerUtils.separator);

		logger.info("");
		logger.info("Ignoring the following patterns");
		logger.info("");

		for (int i = 0; i < ignoredPatterns.size(); i++) {

			String pattern = ignoredPatterns.get(i);

			logger.info("({}/{}) Pattern ignored: {}", i + 1, ignoredPatterns.size(), pattern);
		}
		
		logger.info("");
		logger.info("Completed ");
		
		this.loadJarFiles();
		this.loadDependencyFiles();
	}

	private void loadJarFiles() {

		List<Path> f = FileUtils.getSubFolders(folder, this.ignoredPatterns);
		
		f.stream().forEach(System.out::println);
		
		
		
		logger.info("");
		logger.info(LoggerUtils.title("Loading .java files"));
		logger.info("");
		
		this.jarFiles = FileUtils.getFilesFromFolder(this.folder, this.ignoredPatterns, ".java");
		
		logger.info("Java files found: {}", this.jarFiles.size());
	}

	private void loadDependencyFiles() {

		logger.info("");
		logger.info(LoggerUtils.title("Loading dependency files"));
		logger.info("");
		
		if (Files.exists(this.folder.resolve("pom.xml"))) {
			
			logger.info("Project type detected: Maven");
			
			this.dependencyFiles = new MavenLoader().load(this.folder);
		}
		
		logger.info("");
		logger.info("Dependencies found: {}", this.dependencyFiles.size());
	}

}
