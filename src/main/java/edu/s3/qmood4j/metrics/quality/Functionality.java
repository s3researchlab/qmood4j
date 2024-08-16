package edu.s3.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3.qmood4j.metrics.MetricName;
import edu.s3.qmood4j.metrics.QualityMetric;

public class Functionality implements QualityMetric {

    public MetricName getName() {
        return MetricName.FUNCTIONALITY;
    }

    @Override
    public double calculate(Map<MetricName, Double> mv) {

        double cohesion = mv.getOrDefault(MetricName.COHESION, 0.0);
        double polymorphism = mv.getOrDefault(MetricName.POLYMORPHISM, 0.0);
        double messaging = mv.getOrDefault(MetricName.MESSAGING, 0.0);
        double hierarchies = mv.getOrDefault(MetricName.HIERARCHIES, 0.0);
        double designSize = mv.getOrDefault(MetricName.DESIGN_SIZE, 0.0);

        return 0.12 * cohesion + 0.22 * polymorphism + 0.22 * messaging + 0.22 * designSize + 0.22 * hierarchies;
    }
}