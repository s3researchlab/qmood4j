package edu.s3.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3.qmood4j.metrics.MetricProperty;
import edu.s3.qmood4j.metrics.QualityMetric;

public class Functionality implements QualityMetric {

    public MetricProperty getProperty() {
        return MetricProperty.FUNCTIONALITY;
    }

    @Override
    public double calculate(Map<MetricProperty, Double> mv) {

        double cohesion = mv.getOrDefault(MetricProperty.COHESION, 0.0);
        double polymorphism = mv.getOrDefault(MetricProperty.POLYMORPHISM, 0.0);
        double messaging = mv.getOrDefault(MetricProperty.MESSAGING, 0.0);
        double hierarchies = mv.getOrDefault(MetricProperty.HIERARCHIES, 0.0);
        double designSize = mv.getOrDefault(MetricProperty.DESIGN_SIZE, 0.0);

        return 0.12 * cohesion + 0.22 * polymorphism + 0.22 * messaging + 0.22 * designSize + 0.22 * hierarchies;
    }
}