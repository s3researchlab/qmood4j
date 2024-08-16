package edu.s3.qmood4j.runner;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.s3.qmood4j.metrics.Metric;
import edu.s3.qmood4j.metrics.MetricName;
import edu.s3.qmood4j.metrics.design.AverageNumberOfAncestors;
import edu.s3.qmood4j.metrics.design.ClassInterfaceSize;
import edu.s3.qmood4j.metrics.design.CohesionAmongMethodsOfClass;
import edu.s3.qmood4j.metrics.design.DataAccessMetrics;
import edu.s3.qmood4j.metrics.design.DesignSizeInClasses;
import edu.s3.qmood4j.metrics.design.DirectClassCoupling;
import edu.s3.qmood4j.metrics.design.MeasureOfAggregation;
import edu.s3.qmood4j.metrics.design.MeasureOfFunctionalAbstraction;
import edu.s3.qmood4j.metrics.design.NumberOfHierarchies;
import edu.s3.qmood4j.metrics.design.NumberOfMethods;
import edu.s3.qmood4j.metrics.design.NumberOfPolymorphicMethods;
import edu.s3.qmood4j.metrics.quality.Effectiveness;
import edu.s3.qmood4j.metrics.quality.Extendibility;
import edu.s3.qmood4j.metrics.quality.Flexibility;
import edu.s3.qmood4j.metrics.quality.Functionality;
import edu.s3.qmood4j.metrics.quality.Reusability;
import edu.s3.qmood4j.metrics.quality.Understandability;
import edu.s3.qmood4j.model.ProjectModel;
import edu.s3.qmood4j.utils.LoggerUtils;

public class CodeCalculator {

    private static Logger logger = LogManager.getLogger(CodeCalculator.class);

    private ProjectModel projectModel;

    private Path outputFile;

    private List<Metric> metrics = new ArrayList<>();

    public CodeCalculator(ProjectModel projectModel) {

        this.projectModel = projectModel;

        this.metrics.add(new DesignSizeInClasses());
        this.metrics.add(new NumberOfHierarchies());
        this.metrics.add(new AverageNumberOfAncestors());
        this.metrics.add(new DataAccessMetrics());
        this.metrics.add(new DirectClassCoupling());
        this.metrics.add(new CohesionAmongMethodsOfClass());
        this.metrics.add(new MeasureOfAggregation());
        this.metrics.add(new MeasureOfFunctionalAbstraction());
        this.metrics.add(new NumberOfPolymorphicMethods());
        this.metrics.add(new ClassInterfaceSize());
        this.metrics.add(new NumberOfMethods());
        this.metrics.add(new Reusability());
        this.metrics.add(new Flexibility());
        this.metrics.add(new Understandability());
        this.metrics.add(new Functionality());
        this.metrics.add(new Extendibility());
        this.metrics.add(new Effectiveness());
    }

    public void setOutputFile(Path outputFile) {
        this.outputFile = outputFile;
    }

    public void calculate() {

        LoggerUtils.section("Calculating QMOOD metrics");

        for (int i = 0; i < metrics.size(); i++) {

            Metric metric = metrics.get(i);
            
            projectModel.getMetricValues().put(metric.getName(), metric.calculate(projectModel));
            
            logger.debug("({}/{}) Calculating values for {}", i + 1, metrics.size(), metric.getName().getKey());
        }
        
        LoggerUtils.section("Results");

        Map<MetricName, Double> sortedMetricValues = new TreeMap<>(new Comparator<MetricName>() {

            @Override
            public int compare(MetricName m1, MetricName m2) {
                return m1.getKey().compareToIgnoreCase(m2.getKey());
            }
        });

        sortedMetricValues.putAll(projectModel.getMetricValues());
        
        sortedMetricValues.forEach((key, value) -> {
            logger.debug("%s = %s".formatted(key.getKey(), value));
        });

    }

//    private void savingToFile() {
//
//        logger.debug("");
//        logger.debug(LoggerUtils.title("Saving metrics to output file"));
//
//        StringBuilder builder = new StringBuilder();
//
//        builder.append("# Design Metrics\n");
//
//        designValues.forEach((key, value) -> {
//            builder.append("%s=%s\n".formatted(key, value));
//        });
//
//        builder.append("# Quality Metrics\n");
//
//        qualityValues.forEach((key, value) -> {
//            builder.append("%s=%s\n".formatted(key, value));
//        });
//
//        FileUtils.write(outputFile, builder.toString());
//
//        logger.info("");
//        logger.info("Completed");
//    }
}
