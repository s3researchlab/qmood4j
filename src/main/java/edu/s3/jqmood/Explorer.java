package edu.s3.jqmood;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import edu.s3.jqmood.calculator.MetricValues;
import edu.s3.jqmood.calculator.QMOODCalculator;
import edu.s3.jqmood.model.ProjectModel;
import edu.s3.jqmood.parser.CodeLoader;
import edu.s3.jqmood.parser.CodeParser;
import edu.s3.jqmood.utils.LoggerUtils;

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

        
        logger.info("Scanning project...");
        logger.info("");
		logger.info("Target: {}", folder);
		logger.info("");
		
        CodeLoader loader = new CodeLoader(folder);

        loader.addIgnoredPattern(".*/guava-gwt/.*");
        loader.addIgnoredPattern(".*/android/.*");
        loader.addIgnoredPattern(".*/guava-testlib/.*");
        loader.addIgnoredPattern(".*/guava-tests/.*");

        loader.load();

        List<Path> javaFiles = loader.getJavaFiles();
        List<Path> dependenciesFiles = loader.getDependencyFiles();

        CodeParser parser = new CodeParser();

        for (Path dependencyFile : dependenciesFiles) {
            parser.addLibraries(dependencyFile);
        }

        ProjectModel pm = parser.parse(javaFiles);

        QMOODCalculator calculator = new QMOODCalculator();

        MetricValues values = calculator.calculate(pm);

        values.forEach((key, value) -> {

            logger.info(key + " = " + value);
        });

        logger.info(LoggerUtils.separator);
        logger.info("SUCCESS");
        logger.info(LoggerUtils.separator);
    }

}
