package edu.s3.jqmood.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.s3.jqmood.parser.dependency.DependencyLoader;
import edu.s3.jqmood.parser.dependency.MavenDependencyLoader;
import edu.s3.jqmood.parser.dependency.ReflectionDependencyLoader;
import edu.s3.jqmood.utils.FileUtils;

public class CodeLoader {

    private static Logger logger = LogManager.getLogger(CodeLoader.class);

    private List<String> ignoredPatterns = new ArrayList<>();

    public CodeLoader() {
        this.addIgnoredPattern(".*module-info.java");
        this.addIgnoredPattern(".*package-info.java");
    }

    public void addIgnoredPattern(String pattern) {
        this.ignoredPatterns.add(pattern);
    }
    
    public List<Path> loadFile(Path folder) throws IOException {
        
        return FileUtils.getFilesFromFolder(folder, ignoredPatterns, ".java");
    }

    public SimpleEntry<List<Path>, List<Path>> load(Path folder) throws IOException {

        DependencyLoader loader = null;

        if (Files.exists(folder.resolve("pom.xml"))) {
            loader = new MavenDependencyLoader(folder, ignoredPatterns);
        } else {
            loader = new ReflectionDependencyLoader(folder, ignoredPatterns);
        }

        return loader.load();

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

}
