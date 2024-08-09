package edu.s3.jqmood.loader;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import edu.s3.jqmood.utils.CommandUtils;
import edu.s3.jqmood.utils.FileUtils;

public class MavenLoader {

	private static Logger logger = LogManager.getLogger(MavenLoader.class);
	
	public List<Path> load(Path folder) {

		logger.info("");
		
		List<Path> dependencies = load(folder, String.join(File.separator, List.of("src", "main", "java")));

		logger.info("");
		logger.info("Complete");

		return dependencies;
	}

	private List<Path> load(Path folder, String srcDirectory) {

		logger.info("Analysing: {}", folder);

		Path pomFile = folder.resolve("pom.xml");
		Path jarsFolder = folder.resolve("target", "dependencies");

		if (!Files.exists(pomFile)) {
			return List.of();
		}

		Model model = readPomFile(pomFile);

		if (model.getBuild() != null && model.getBuild().getSourceDirectory() != null) {
			srcDirectory = model.getBuild().getSourceDirectory();
		}

		List<Path> dependencies = new ArrayList<>();

		if (model.getPackaging().equalsIgnoreCase("pom")) {

			List<Path> deps = new ArrayList<>();
			
			for (String module : model.getModules()) {
				dependencies.addAll(load(folder.resolve(module), srcDirectory));
			}
			
			for (String module : model.getModules()) {
				
			}
			
		}

		if (Files.exists(folder.resolve(srcDirectory))) {
			dependencies.add(folder.resolve(srcDirectory));
		}

		if (!Files.exists(jarsFolder)) {

			FileUtils.createIfNotExists(jarsFolder);

			for (Dependency dependency : model.getDependencies()) {
				downloadJarFile(folder, jarsFolder, dependency);
			}

			if (model.getDependencyManagement() != null) {

				for (Dependency dependency : model.getDependencyManagement().getDependencies()) {
					downloadJarFile(folder, jarsFolder, dependency);
				}
			}
		}

		dependencies.addAll(FileUtils.getFilesFromFolder(jarsFolder, ".jar"));

		return dependencies;
	}

	private Model readPomFile(Path pomFile) {

		checkNotNull(pomFile);
		checkArgument(Files.exists(pomFile), pomFile + " not found");

		MavenXpp3Reader reader = new MavenXpp3Reader();

		try {

			Model model = reader.read(new FileReader(pomFile.toFile()));

			model.setPomFile(pomFile.toFile());

			return model;
		} catch (IOException | XmlPullParserException e) {
			throw new RuntimeException(e);
		}
	}

	private void downloadJarFile(Path folder, Path jarFolder, Dependency dependency) {

		String groupId = dependency.getGroupId();
		String artifactId = dependency.getArtifactId();
		String version = dependency.getVersion();

		if (version == null) {
			return;
		}

		String artifact = "-Dartifact=%s:%s:%s:jar".formatted(groupId, artifactId, version);
		String program = "/opt/homebrew/bin/mvn";
		String plugin = "org.apache.maven.plugins:maven-dependency-plugin:copy";
		String output = "-DoutputDirectory=%s".formatted(jarFolder);

		List<String> args = List.of(program, "-f", folder.toString(), plugin, artifact, output);

		CommandUtils.run(args);
	}

}
