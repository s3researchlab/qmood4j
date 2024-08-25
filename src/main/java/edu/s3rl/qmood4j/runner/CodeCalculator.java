package edu.s3rl.qmood4j.runner;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import edu.s3rl.qmood4j.metrics.Metric;
import edu.s3rl.qmood4j.metrics.MetricName;
import edu.s3rl.qmood4j.metrics.design.AverageNumberOfAncestors;
import edu.s3rl.qmood4j.metrics.design.ClassInterfaceSize;
import edu.s3rl.qmood4j.metrics.design.CohesionAmongMethodsOfClass;
import edu.s3rl.qmood4j.metrics.design.DataAccessMetrics;
import edu.s3rl.qmood4j.metrics.design.DesignSizeInClasses;
import edu.s3rl.qmood4j.metrics.design.DirectClassCoupling;
import edu.s3rl.qmood4j.metrics.design.MeasureOfAggregation;
import edu.s3rl.qmood4j.metrics.design.MeasureOfFunctionalAbstraction;
import edu.s3rl.qmood4j.metrics.design.NumberOfHierarchies;
import edu.s3rl.qmood4j.metrics.design.NumberOfMethods;
import edu.s3rl.qmood4j.metrics.design.NumberOfPolymorphicMethods;
import edu.s3rl.qmood4j.metrics.quality.Effectiveness;
import edu.s3rl.qmood4j.metrics.quality.Extendibility;
import edu.s3rl.qmood4j.metrics.quality.Flexibility;
import edu.s3rl.qmood4j.metrics.quality.Functionality;
import edu.s3rl.qmood4j.metrics.quality.Reusability;
import edu.s3rl.qmood4j.metrics.quality.TotalQualityIndex;
import edu.s3rl.qmood4j.metrics.quality.Understandability;
import edu.s3rl.qmood4j.model.ClassModel;
import edu.s3rl.qmood4j.model.ProjectModel;
import edu.s3rl.qmood4j.settings.Settings;
import edu.s3rl.qmood4j.utils.FileUtils;
import edu.s3rl.qmood4j.utils.LoggerUtils;

public class CodeCalculator {

    private static Logger logger = LogManager.getLogger(CodeCalculator.class);

    private ProjectModel projectModel;

    private Path outputFile;

    private Set<Metric> metrics = new TreeSet<>(new Comparator<Metric>() {

        @Override
        public int compare(Metric m1, Metric m2) {
            return m1.getName().getKey().compareToIgnoreCase(m2.getName().getKey());
        }
    });

    public CodeCalculator(CodeParser parser) {

        this(parser, null);
    }

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

        int counter = 1;

        for (Metric metric : this.metrics) {

            projectModel.metrics.put(metric.getName(), metric.calculate(projectModel));

            logger.info("({}/{}) Calculating values for {}", counter++, metrics.size(), metric.getName().getKey());
        }

        for (ClassModel cm : projectModel.getClassModels().values()) {
            cm.metrics.put(MetricName.REUSABILITY, new Reusability().calculate(cm.metrics));
            cm.metrics.put(MetricName.FLEXIBILITY, new Flexibility().calculate(cm.metrics));
            cm.metrics.put(MetricName.UNDERSTANDABILITY, new Understandability().calculate(cm.metrics));
            cm.metrics.put(MetricName.FUNCTIONALITY, new Functionality().calculate(cm.metrics));
            cm.metrics.put(MetricName.EXTENDIBILITY, new Extendibility().calculate(cm.metrics));
            cm.metrics.put(MetricName.EFFECTIVENESS, new Effectiveness().calculate(cm.metrics));
            cm.metrics.put(MetricName.TQI, new TotalQualityIndex().calculate(cm.metrics));
        }

        LoggerUtils.section("Results");

        FileUtils.write(Settings.getMetricsOverviewFile(), generateOverviewMetrics());
        FileUtils.write(Settings.getMetricsDetailedFile(), generateDetailedMetrics());
    }

    private String generateOverviewMetrics() {

        StringBuilder builder = new StringBuilder();

        builder.append("# Folder: %s\n".formatted(Settings.folder));
        builder.append("# %s\n".formatted(Settings.getDateTimeNow()));

        for (Metric metric : this.metrics) {

            MetricName name = metric.getName();

            double value = projectModel.metrics.get(name);

            String output = "%s = %s".formatted(name.getKey(), value);

            builder.append(output + "\n");
            logger.info(output);
        }

        return builder.toString();
    }

    private String generateDetailedMetrics() {

        StringBuilder builder = new StringBuilder("class_name,");

        List<String> keys = this.metrics.stream().map(e -> e.getName().toString().toLowerCase()).toList();

        builder.append(Strings.join(keys, ',')).append("\n");

        for (ClassModel clsModel : projectModel.getClassModels().values()) {

            builder.append(clsModel.getFullClassName()).append(",");

            for (Metric metric : this.metrics) {

                MetricName name = metric.getName();

                double value = 0.0;

                if (clsModel.metrics.containsKey(name)) {
                    value = clsModel.metrics.get(name);
                }

                builder.append(value).append(",");
            }

            builder.append("\n");
        }

        return builder.toString();
    }
}
