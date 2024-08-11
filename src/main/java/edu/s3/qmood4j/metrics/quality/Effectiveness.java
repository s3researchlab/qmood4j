package edu.s3.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3.qmood4j.metrics.MetricProperty;
import edu.s3.qmood4j.metrics.QualityMetric;

public class Effectiveness implements QualityMetric {

    public MetricProperty getProperty() {
        return MetricProperty.EFFECTIVENESS;
    }

    @Override
    public double calculate(Map<MetricProperty, Double> mv) {

        double abstraction = mv.getOrDefault(MetricProperty.ABSTRACTION, 0.0);
        double encapsulation = mv.getOrDefault(MetricProperty.ENCAPSULATION, 0.0);
        double composition = mv.getOrDefault(MetricProperty.COMPOSITION, 0.0);
        double inheritance = mv.getOrDefault(MetricProperty.INHERITANCE, 0.0);
        double polymorphism = mv.getOrDefault(MetricProperty.POLYMORPHISM, 0.0);

        return 0.2 * abstraction + 0.2 * encapsulation + 0.2 * composition + 0.2 * inheritance + 0.2 * polymorphism;
    }
}