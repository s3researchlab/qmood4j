package edu.s3.jqmood.calculator.metrics.design;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.Test;

import edu.s3.jqmood.calculator.MetricValues;
import edu.s3.jqmood.calculator.QMOODCalculator;
import edu.s3.jqmood.calculator.metrics.MetricProperty;
import edu.s3.jqmood.model.ProjectModel;
import edu.s3.jqmood.parser.CodeParser;
import edu.s3.jqmood.utils.MavenUtils;

public class TestClassInterfaceSize {

    private Path path = Paths.get(System.getProperty("user.dir"));

    private Path resourcesPath = path.resolve("src", "test", "resources");

    private Path metricPath = resourcesPath.resolve("metrics", "class-interface-size");

    private double readProjectModel(Path folder, MetricProperty mt) throws IOException {

        Configurator.setRootLevel(Level.DEBUG);

        CodeParser parser = new CodeParser();

        parser.addLibraries(folder.resolve("src/main/java"));

        for (Path dependency : MavenUtils.getJarFilePathsFromPomFile(folder)) {
            parser.addLibraries(dependency);
        }

//        ProjectModel pm = parser.parse(folder);
//
//        QMOODCalculator calculator = new QMOODCalculator();
//
//        MetricValues values = calculator.calculate(pm);

//        return values.get(mt);
        return 1;
    }

//    @Test
//    void shouldReturn1() throws IOException {
//        assertEquals(1, readProjectModel(metricPath.resolve("project-a"), MetricProperty.MESSAGING));
//    }
//
//    @Test
//    void shouldReturn1And5() throws IOException {
//        assertEquals(1.5, readProjectModel(metricPath.resolve("project-b"), MetricProperty.MESSAGING));
//    }
//    
//    @Test
//    void shouldReturn1And53() throws IOException {
//        assertEquals(1.5, readProjectModel(metricPath.resolve("project-c"), MetricProperty.MESSAGING));
//    }
}
