package edu.s3.jqmood.calculator.metrics.quality;

import edu.s3.jqmood.calculator.MetricValues;
import edu.s3.jqmood.calculator.metrics.MetricProperty;

public class Effectiveness extends QualityMetric {

    public MetricProperty getProperty() {
        return MetricProperty.EFFECTIVENESS;
    }

    @Override
    public double calculate(MetricValues mv) {

        double abstraction = mv.get(MetricProperty.ABSTRACTION);
        double encapsulation = mv.get(MetricProperty.ENCAPSULATION);
        double composition = mv.get(MetricProperty.COMPOSITION);
        double inheritance = mv.get(MetricProperty.INHERITANCE);
        double polymorphism = mv.get(MetricProperty.POLYMORPHISM);

        return 0.2 * abstraction + 0.2 * encapsulation + 0.2 * composition + 0.2 * inheritance + 0.2 * polymorphism;
    }
}