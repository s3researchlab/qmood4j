package edu.s3.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3.qmood4j.metrics.MetricProperty;
import edu.s3.qmood4j.metrics.QualityMetric;

public class Extendibility implements QualityMetric {

    public MetricProperty getProperty() {
        return MetricProperty.EXTENDIBILITY;
    }

    @Override
    public double calculate(Map<MetricProperty, Double> mv) {

        double abstraction = mv.getOrDefault(MetricProperty.ABSTRACTION, 0.0);
        double coupling = mv.getOrDefault(MetricProperty.COUPLING, 0.0);
        double inheritance = mv.getOrDefault(MetricProperty.INHERITANCE, 0.0);
        double polymorphism = mv.getOrDefault(MetricProperty.POLYMORPHISM, 0.0);

        return 0.5 * abstraction - 0.5 * coupling + 0.5 * inheritance + 0.5 * polymorphism;
    }
}