package edu.s3.qmood4j.runner;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.s3.qmood4j.metrics.DesignMetric;
import edu.s3.qmood4j.metrics.MetricProperty;
import edu.s3.qmood4j.metrics.QualityMetric;
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

    private List<DesignMetric> designMetrics = new ArrayList<>();

    private List<QualityMetric> qualityMetrics = new ArrayList<>();

    private Map<MetricProperty, Double> designValues = new HashMap<>();

    private Map<MetricProperty, Double> qualityValues = new HashMap<>();

    public CodeCalculator(ProjectModel projectModel) {

        logger.info(LoggerUtils.separator);
        logger.info(LoggerUtils.green("Code Calculator"));
        logger.info(LoggerUtils.separator);

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

    public Map<MetricProperty, Double> getDesignValues() {
        return this.designValues;
    }

    public Map<MetricProperty, Double> getQualityValues() {
        return this.qualityValues;
    }

    public void setOutputFile(Path outputFile) {
        this.outputFile = outputFile;
    }

    public void calculate() {

        logger.info("");
        logger.info(LoggerUtils.title("Calculating design metrics"));

        for (DesignMetric metric : designMetrics) {
            this.designValues.put(metric.getProperty(), metric.calculate(projectModel));
        }

        logger.info("");
        logger.info("Completed");
        logger.info("");
        logger.info(LoggerUtils.title("Calculating quality metrics"));

        for (QualityMetric metric : qualityMetrics) {
            qualityValues.put(metric.getProperty(), metric.calculate(designValues));
        }

        logger.info("");
        logger.info("Completed");
        logger.info("");
        logger.info(LoggerUtils.title("Metrics"));

        logger.info("");
        logger.info("Design Metrics");
        logger.info("");

        designValues.forEach((key, value) -> {
            logger.info("%s=%s".formatted(key, value));
        });

        logger.info("");
        logger.info("Quality Metrics");
        logger.info("");

        qualityValues.forEach((key, value) -> {
            logger.info("%s=%s".formatted(key, value));
        });

        savingToFile();
    }

    private void savingToFile() {

        logger.info("");
        logger.info(LoggerUtils.title("Saving metrics to output file"));

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
