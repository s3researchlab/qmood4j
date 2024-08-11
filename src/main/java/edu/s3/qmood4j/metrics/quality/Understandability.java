package edu.s3.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3.qmood4j.metrics.MetricProperty;
import edu.s3.qmood4j.metrics.QualityMetric;

public class Understandability implements QualityMetric {

    public MetricProperty getProperty() {
        return MetricProperty.UNDERSTANDABILITY;
    }

    @Override
    public double calculate(Map<MetricProperty, Double> mv) {

        double abstraction = mv.getOrDefault(MetricProperty.ABSTRACTION, 0.0);
        double encapsulation = mv.getOrDefault(MetricProperty.ENCAPSULATION, 0.0);
        double coupling = mv.getOrDefault(MetricProperty.COUPLING, 0.0);
        double cohesion = mv.getOrDefault(MetricProperty.COHESION, 0.0);
        double polymorphism = mv.getOrDefault(MetricProperty.POLYMORPHISM, 0.0);
        double complexity = mv.getOrDefault(MetricProperty.COMPLEXITY, 0.0);
        double designSize = mv.getOrDefault(MetricProperty.DESIGN_SIZE, 0.0);

        return -0.33 * abstraction + 0.33 * encapsulation - 0.33 * coupling + 0.33 * cohesion - 0.33 * polymorphism - 0.33 * complexity - 0.33 * designSize;
    }
}