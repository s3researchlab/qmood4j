package edu.s3.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3.qmood4j.metrics.MetricProperty;
import edu.s3.qmood4j.metrics.QualityMetric;

public class Reusability implements QualityMetric {

    public MetricProperty getProperty() {
        return MetricProperty.REUSABILITY;
    }

    @Override
    public double calculate(Map<MetricProperty, Double> mv) {

        double coupling = mv.getOrDefault(MetricProperty.COUPLING, 0.0);
        double cohesion = mv.getOrDefault(MetricProperty.COHESION, 0.0);
        double messaging = mv.getOrDefault(MetricProperty.MESSAGING, 0.0);
        double designSize = mv.getOrDefault(MetricProperty.DESIGN_SIZE, 0.0);

        return -0.25 * coupling + 0.25 * cohesion + 0.5 * messaging + 0.5 * designSize;
    }
}