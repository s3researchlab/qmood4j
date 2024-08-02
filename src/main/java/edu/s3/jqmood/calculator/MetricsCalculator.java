package edu.s3.jqmood.calculator;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.s3.jqmood.calculator.metrics.design.DesignMetric;
import edu.s3.jqmood.calculator.metrics.quality.QualityMetric;
import edu.s3.jqmood.model.ProjectModel;

public class MetricsCalculator {

    private static Logger logger = LogManager.getLogger(MetricsCalculator.class);

    private List<DesignMetric> designMetrics = new ArrayList<>();

    private List<QualityMetric> qualityMetrics = new ArrayList<>();

    public void addDesignMetric(DesignMetric metric) {
        this.designMetrics.add(metric);
    }

    public void addQualityMetric(QualityMetric metric) {
        this.qualityMetrics.add(metric);
    }

    public MetricValues calculate(ProjectModel pm) {

        logger.info("Calculating {} design and {} quality metrics", designMetrics.size(), qualityMetrics.size());

        MetricValues mv = new MetricValues();

        for (DesignMetric metric : designMetrics) {
            mv.put(metric.getProperty(), metric.calculate(pm));
        }

        for (QualityMetric metric : qualityMetrics) {
            mv.put(metric.getProperty(), metric.calculate(mv));
        }

        logger.info("Done");

        return mv;
    }

}
