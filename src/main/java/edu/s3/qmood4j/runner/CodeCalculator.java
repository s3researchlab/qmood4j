package edu.s3.qmood4j.runner;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import edu.s3.qmood4j.utils.FileUtils;
import edu.s3.qmood4j.utils.LoggerUtils;

public class CodeCalculator {

    private static Logger logger = LogManager.getLogger(CodeCalculator.class);

    private ProjectModel projectModel;

    private Path outputFile;
    
    private List<Metric> designMetrics = new ArrayList<>();

    private List<Metric> qualityMetrics = new ArrayList<>();

    private Map<MetricName, Double> designValues = new HashMap<>();

    private Map<MetricName, Double> qualityValues = new HashMap<>();

    public CodeCalculator(ProjectModel projectModel) {

        this.projectModel = projectModel;

        this.designMetrics.add(new DesignSizeInClasses());
        this.designMetrics.add(new NumberOfHierarchies());
        this.designMetrics.add(new AverageNumberOfAncestors());
        this.designMetrics.add(new DataAccessMetrics());
        this.designMetrics.add(new DirectClassCoupling());
        this.designMetrics.add(new CohesionAmongMethodsOfClass());
        this.designMetrics.add(new MeasureOfAggregation());
        this.designMetrics.add(new MeasureOfFunctionalAbstraction());
        this.designMetrics.add(new NumberOfPolymorphicMethods());
        this.designMetrics.add(new ClassInterfaceSize());
        this.designMetrics.add(new NumberOfMethods());

        this.qualityMetrics.add(new Reusability());
        this.qualityMetrics.add(new Flexibility());
        this.qualityMetrics.add(new Understandability());
        this.qualityMetrics.add(new Functionality());
        this.qualityMetrics.add(new Extendibility());
        this.qualityMetrics.add(new Effectiveness());
    }

    public Map<MetricName, Double> getDesignValues() {
        return this.designValues;
    }

    public Map<MetricName, Double> getQualityValues() {
        return this.qualityValues;
    }

    public void setOutputFile(Path outputFile) {
        this.outputFile = outputFile;
    }

    public void calculate() {

        LoggerUtils.section("Calculating design metrics");
                
        for (Metric metric : designMetrics) {
            this.designValues.put(metric.getName(), metric.calculate(projectModel));
        }
        
        LoggerUtils.section("Calculating quality metrics");
        
        for (Metric metric : qualityMetrics) {
            qualityValues.put(metric.getName(), metric.calculate(designValues));
        }

        designValues.forEach((key, value) -> {
            logger.debug("%s = %s".formatted(key.getKey(), value));
        });

        qualityValues.forEach((key, value) -> {
            logger.debug("%s = %s".formatted(key.getKey(), value));
        });

        savingToFile();
    }

    private void savingToFile() {

        logger.debug("");
        logger.debug(LoggerUtils.title("Saving metrics to output file"));

        StringBuilder builder = new StringBuilder();

        builder.append("# Design Metrics\n");

        designValues.forEach((key, value) -> {
            builder.append("%s=%s\n".formatted(key, value));
        });

        builder.append("# Quality Metrics\n");

        qualityValues.forEach((key, value) -> {
            builder.append("%s=%s\n".formatted(key, value));
        });

        FileUtils.write(outputFile, builder.toString());

        logger.info("");
        logger.info("Completed");
    }
}
