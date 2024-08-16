package edu.s3.qmood4j.utils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class MavenUtils {

    private static Logger logger = LogManager.getLogger(MavenUtils.class);

    /**
     * Private Constructor will prevent the instantiation of this class directly
     */
    private MavenUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static Model readPomFile(Path pomFile) {

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

    public static void downloadDependencies(Model model, Path jarsFolder) {

        if (!Files.exists(jarsFolder)) {
            
            logger.info("");
            logger.info("Downloading .jar dependency files");

            FileUtils.createFolder(jarsFolder);

            Path pomFile = model.getPomFile().toPath();

            for (Dependency dependency : model.getDependencies()) {
                MavenUtils.downloadJarFile(pomFile, jarsFolder, dependency);
            }

            if (model.getDependencyManagement() != null) {

                for (Dependency dependency : model.getDependencyManagement().getDependencies()) {
                    MavenUtils.downloadJarFile(pomFile, jarsFolder, dependency);
                }
            }
            
            logger.info("");
            logger.info("Completed ");
        }
    }

    public static void downloadJarFile(Path pomFile, Path jarFolder, Dependency dependency) {

        String groupId = dependency.getGroupId();
        String artifactId = dependency.getArtifactId();
        String version = dependency.getVersion();

        if (version == null) {
            return;
        }

        String artifact = "-Dartifact=%s:%s:%s:jar".formatted(groupId, artifactId, version);
        String program = "/opt/homebrew/bin/mvn";
//        String plugin = "org.apache.maven.plugins:maven-dependency-plugin:copy";

//        String plugin = "dependency:sources";
        String plugin = "dependency:copy-dependencies";

        String output = "-DoutputDirectory=%s".formatted(jarFolder);

        List<String> args = List.of(program, "-f", pomFile.toString(), plugin, artifact, output);

        CommandUtils.run(args);
    }
}
