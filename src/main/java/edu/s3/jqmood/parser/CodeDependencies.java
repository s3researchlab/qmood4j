package edu.s3.jqmood.parser;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

public class CodeDependencies {

    private static Logger logger = LogManager.getLogger(CodeDependencies.class);

    private List<Path> loadMaven(Path folder) throws IOException {

        logger.info("processing: {}", folder);

        List<Path> dependencies = new ArrayList<>();

        Model model = readPomFile(folder.resolve("pom.xml"));

        for (String module : model.getModules()) {
            dependencies.addAll(loadMaven(folder.resolve(module)));
        }

        Path jarsFolder = folder.resolve("target", "dependencies");

        if (!Files.exists(jarsFolder)) {

            FileUtils.createIfNotExists(jarsFolder);

            for (Dependency dependency : model.getDependencies()) {
                download(folder, jarsFolder, dependency);
            }

            for (Dependency dependency : model.getDependencyManagement().getDependencies()) {
                download(folder, jarsFolder, dependency);
            }
        }

        dependencies.addAll(FileUtils.getFilesFromFolder(jarsFolder, ".jar"));

        return dependencies;
    }

    private void download(Path folder, Path jarFolder, Dependency dependency) {

        String groupId = dependency.getGroupId();
        String artifactId = dependency.getArtifactId();
        String version = dependency.getVersion();

        if (version == null) {
            return;
        }

        String artifact = "-Dartifact=%s:%s:%s:jar".formatted(groupId, artifactId, version);
        String program = "/opt/homebrew/bin/mvn";
        String plugin = "org.apache.maven.plugins:maven-dependency-plugin:copy";
        String output = "-DoutputDirectory=" + jarFolder;

        List<String> args = List.of(program, "-f", folder.toString(), plugin, artifact, output);

        CommandUtils.run(args);
    }

    public List<Path> load(Path folder, List<Path> files) throws IOException {

        List<Path> dependencies = new ArrayList<>();

        if (Files.exists(folder.resolve("pom.xml"))) {
            dependencies.addAll(loadMaven(folder));
        }

        return dependencies;// loader.load();

//        logger.info("Getting all java files");
//
//        ignoredPatterns.stream().forEach(e -> logger.info("Files ignored: {}", e));

//        List<Path> files = FileUtils.getFilesFromFolder(folder, ignoredPatterns, ".java");
//        List<Path> dependencies = readDependencies(folder, files);
//
//        logger.info("Found {} java files", files.size());
//        logger.info("Found {} dependency files", dependencies.size());

//        List<Path> files = new ArrayList<>();
//        List<Path> dependencies = new ArrayList<>();
//
//        return new SimpleEntry<>(files, dependencies);
    }
//
//    private SimpleEntry<List<Path>, List<Path>> loadDependencies(Path folder) throws IOException {
//
//        List<Path> files = new ArrayList<>();
//        List<Path> dependencies = new ArrayList<>();
//
//        return new SimpleEntry<>(files, dependencies);
//    }
//
//    private SimpleEntry<List<Path>, List<Path>> loadMavenDependencies(Path folder) throws IOException {
//
//        logger.info("Loading Maven Dependencies: {}", folder);
//
//        Model model = MavenUtils.readPomFile(folder.resolve("pom.xml"));
//
//        if (MavenUtils.isParentProject(model)) {
//            for (String module : model.getModules()) {
//                loadMavenDependencies(folder.resolve(module));
//            }
//        } else {
//
//            List<Path> javaFiles = FileUtils.getFilesFromFolder(folder, ignoredPatterns, ".java");
//
//            List<Path> dependencies = new ArrayList<>();
//
//            List<DependencyLoader> depLoaders = new ArrayList<>();
//
//            depLoaders.add(new MavenDependencyLoader(folder));
////            depLoaders.add(new ReflectionDependencyLoader(javaFiles));
//
//            for (DependencyLoader depLoader : depLoaders) {
//                dependencies.addAll(depLoader.load());
//            }
//
////            System.out.println("files");
////            javaFiles.stream().forEach(System.out::println);
//            System.out.println(">>>>>>dependencies");
//            dependencies.stream().forEach(System.out::println);
//        }
//
//        return new SimpleEntry<>(files, dependencies);
//    }

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

}
