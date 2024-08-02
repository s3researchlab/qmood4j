package edu.s3.jqmood;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import edu.s3.jqmood.calculator.MetricValues;
import edu.s3.jqmood.calculator.MetricsCalculator;
import edu.s3.jqmood.calculator.QMOODCalculator;
import edu.s3.jqmood.calculator.metrics.design.AverageNumberOfAncestors;
import edu.s3.jqmood.calculator.metrics.design.ClassInterfaceSize;
import edu.s3.jqmood.calculator.metrics.design.CohesionAmongMethodsOfClass;
import edu.s3.jqmood.calculator.metrics.design.DataAccessMetrics;
import edu.s3.jqmood.calculator.metrics.design.DesignSizeInClasses;
import edu.s3.jqmood.calculator.metrics.design.DirectClassCoupling;
import edu.s3.jqmood.calculator.metrics.design.MeasureOfAggregation;
import edu.s3.jqmood.calculator.metrics.design.MeasureOfFunctionalAbstraction;
import edu.s3.jqmood.calculator.metrics.design.NumberOfHierarchies;
import edu.s3.jqmood.calculator.metrics.design.NumberOfMethods;
import edu.s3.jqmood.calculator.metrics.design.NumberOfPolymorphicMethods;
import edu.s3.jqmood.calculator.metrics.quality.Effectiveness;
import edu.s3.jqmood.calculator.metrics.quality.Extendibility;
import edu.s3.jqmood.calculator.metrics.quality.Flexibility;
import edu.s3.jqmood.calculator.metrics.quality.Functionality;
import edu.s3.jqmood.calculator.metrics.quality.Reusability;
import edu.s3.jqmood.calculator.metrics.quality.Understandability;
import edu.s3.jqmood.model.ProjectModel;
import edu.s3.jqmood.parser.SourceCodeParser;
import edu.s3.jqmood.utils.MavenUtils;

public class Explorer {

    private static Logger logger = LogManager.getLogger(Explorer.class);

    public static void main(String[] args) throws IOException {

        Configurator.setRootLevel(Level.DEBUG);

        Path folder = Path.of("/Users/thiagodnf/Workspace/toy");
//        Path folder = Path.of("/Users/thiagodnf/Workspace/hangman-in-javafx");

        SourceCodeParser parser = new SourceCodeParser();

        parser.addIgnoredPattern(".*test.*");
        parser.addIgnoredPattern(".*target.*");
        parser.addIgnoredPattern(".*module-info.java");
        parser.addIgnoredPattern(".*package-info.java");
        parser.addIgnoredPattern(".*Explorer.java");

        parser.addLibraries(folder.resolve("src/main/java"));

        for (Path dependency : MavenUtils.getJarFilePathsFromPomFile(folder)) {
            parser.addLibraries(dependency);
        }

        ProjectModel pm = parser.parse(folder);

        QMOODCalculator calculator = new QMOODCalculator();

        MetricValues values = calculator.calculate(pm);

        values.forEach((key, value) -> {

            logger.info(key + " = " + value);
        });

        logger.info("Done.");
    }

}
