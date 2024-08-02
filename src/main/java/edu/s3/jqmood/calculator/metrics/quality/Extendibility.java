package edu.s3.jqmood.calculator.metrics.quality;

import edu.s3.jqmood.calculator.MetricValues;
import edu.s3.jqmood.calculator.metrics.MetricProperty;

public class Extendibility extends QualityMetric {

    public MetricProperty getProperty() {
        return MetricProperty.EXTENDIBILITY;
    }

    @Override
    public double calculate(MetricValues mv) {

        double abstraction = mv.get(MetricProperty.ABSTRACTION);
        double coupling = mv.get(MetricProperty.COUPLING);
        double inheritance = mv.get(MetricProperty.INHERITANCE);
        double polymorphism = mv.get(MetricProperty.POLYMORPHISM);

        return 0.5 * abstraction - 0.5 * coupling + 0.5 * inheritance + 0.5 * polymorphism;
    }
}