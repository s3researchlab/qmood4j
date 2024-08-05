package edu.s3.jqmood;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import edu.s3.jqmood.model.ProjectModel;
import edu.s3.jqmood.parser.CodeLoader;
import edu.s3.jqmood.parser.CodeParser;

public class Explorer {

    private static Logger logger = LogManager.getLogger(Explorer.class);

    public static void main(String[] args) throws IOException {

        Configurator.setRootLevel(Level.DEBUG);

//        Path folder = Path.of("/Users/thiagodnf/Workspace/grammatical-evolution");
//        Path folder = Path.of("/Users/thiagodnf/Workspace/jedit");
//        Path folder = Path.of("/Users/thiagodnf/Workspace/toy");
//        Path folder = Path.of("/Users/thiagodnf/Workspace/jhotdraw-10.1");
        Path folder = Path.of("/Users/thiagodnf/Workspace/guava-33.2.1");
//        Path folder = Path.of("/Users/thiagodnf/Workspace/gson");
//        Path folder = Path.of("/Users/thiagodnf/Workspace/jackrabbit");
//        Path folder = Path.of("/Users/thiagodnf/Workspace/nautilus-framework");
//        Path folder = Path.of("/Users/thiagodnf/Workspace/hangman-in-javafx");

//        Path folder = Path.of("/Users/thiagodnf/Workspace/toy");

        logger.info("Folder: {}", folder);

        CodeLoader loader = new CodeLoader();

        loader.addIgnoredPattern(".*/src/test/java/.*");
        loader.addIgnoredPattern(".*Test.java");
        
        loader.addIgnoredPattern(".*/guava-gwt/.*");
        loader.addIgnoredPattern(".*/android/.*");
        loader.addIgnoredPattern(".*/guava-testlib/.*");
        loader.addIgnoredPattern(".*/guava-tests/.*");

        List<Path> fi = loader.loadFile(folder);

        System.out.println("files");
        fi.stream().forEach(System.out::println);
//        
//        loader.addIgnoredPattern(".*/target/.*");

//        SimpleEntry<List<Path>, List<Path>> output = loader.load(folder);

//        \h(System.out::println);

//        System.out.println(DependencyLoader.load(files));

//        CodeParser parser = new CodeParser();

//        System.out.println(DependencyLoader.load(folder));
//
//        parser.addLibraries(folder.resolve("src/main/java"));
//
//        for (Path dependency : MavenUtils.getJarFilePathsFromPomFile(folder)) {
//            parser.addLibraries(dependency);
//        }
//
//        ProjectModel pm = parser.parse(files);
//
//        QMOODCalculator calculator = new QMOODCalculator();
//
//        MetricValues values = calculator.calculate(pm);
//
//        values.forEach((key, value) -> {
//
//            logger.info(key + " = " + value);
//        });
//
//        logger.info("Done.");
    }

}
