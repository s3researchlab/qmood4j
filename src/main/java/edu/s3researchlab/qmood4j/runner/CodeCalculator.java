package edu.s3researchlab.qmood4j.runner;

import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.s3researchlab.qmood4j.metrics.Metric;
import edu.s3researchlab.qmood4j.metrics.MetricName;
import edu.s3researchlab.qmood4j.metrics.design.AverageNumberOfAncestors;
import edu.s3researchlab.qmood4j.metrics.design.ClassInterfaceSize;
import edu.s3researchlab.qmood4j.metrics.design.CohesionAmongMethodsOfClass;
import edu.s3researchlab.qmood4j.metrics.design.DataAccessMetrics;
import edu.s3researchlab.qmood4j.metrics.design.DesignSizeInClasses;
import edu.s3researchlab.qmood4j.metrics.design.DirectClassCoupling;
import edu.s3researchlab.qmood4j.metrics.design.MeasureOfAggregation;
import edu.s3researchlab.qmood4j.metrics.design.MeasureOfFunctionalAbstraction;
import edu.s3researchlab.qmood4j.metrics.design.NumberOfHierarchies;
import edu.s3researchlab.qmood4j.metrics.design.NumberOfMethods;
import edu.s3researchlab.qmood4j.metrics.design.NumberOfPolymorphicMethods;
import edu.s3researchlab.qmood4j.metrics.quality.Effectiveness;
import edu.s3researchlab.qmood4j.metrics.quality.Extendibility;
import edu.s3researchlab.qmood4j.metrics.quality.Flexibility;
import edu.s3researchlab.qmood4j.metrics.quality.Functionality;
import edu.s3researchlab.qmood4j.metrics.quality.Reusability;
import edu.s3researchlab.qmood4j.metrics.quality.TotalQualityIndex;
import edu.s3researchlab.qmood4j.metrics.quality.Understandability;
import edu.s3researchlab.qmood4j.model.ProjectModel;
import edu.s3researchlab.qmood4j.settings.Settings;
import edu.s3researchlab.qmood4j.utils.FileUtils;
import edu.s3researchlab.qmood4j.utils.LoggerUtils;

public class CodeCalculator {

    private static Logger logger = LogManager.getLogger(CodeCalculator.class);

    private ProjectModel projectModel;

    private Path outputFile;

    private List<Metric> metrics = new ArrayList<>();

    public CodeCalculator(CodeParser parser, Path outputFile) {

        this.projectModel = parser.getProjectModel();
        this.outputFile = outputFile;

        // Design Metrics
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

        // Quality Metrics
        this.metrics.add(new Reusability());
        this.metrics.add(new Flexibility());
        this.metrics.add(new Understandability());
        this.metrics.add(new Functionality());
        this.metrics.add(new Extendibility());
        this.metrics.add(new Effectiveness());

        this.metrics.add(new TotalQualityIndex());
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

        StringBuilder builder = new StringBuilder();

        builder.append("# Folder: %s\n".formatted(Settings.folder));
        builder.append("# %s\n".formatted(ZonedDateTime.now().toString()));

        sortedMetricValues.forEach((key, value) -> {
            builder.append("%s = %s\n".formatted(key.getKey(), value));
        });

        String content = builder.toString();

        if (outputFile == null) {
            outputFile = Settings.getDefaultOutputFile();
        }

        FileUtils.write(outputFile, content.toString());

        System.out.println(content);
    }
}
