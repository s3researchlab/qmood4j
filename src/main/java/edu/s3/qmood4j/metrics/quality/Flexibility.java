package edu.s3.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3.qmood4j.metrics.MetricProperty;
import edu.s3.qmood4j.metrics.QualityMetric;

public class Flexibility implements QualityMetric {

    public MetricProperty getProperty() {
        return MetricProperty.FLEXIBILITY;
    }

    @Override
    public double calculate(Map<MetricProperty, Double> mv) {

        double encapsulation = mv.getOrDefault(MetricProperty.ENCAPSULATION, 0.0);
        double coupling = mv.getOrDefault(MetricProperty.COUPLING, 0.0);
        double composition = mv.getOrDefault(MetricProperty.COMPOSITION, 0.0);
        double polymorphism = mv.getOrDefault(MetricProperty.POLYMORPHISM, 0.0);

        return 0.25 * encapsulation - 0.25 * coupling + 0.5 * composition + 0.5 * polymorphism;
    }
}