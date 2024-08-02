package edu.s3.jqmood.calculator.metrics.quality;

import edu.s3.jqmood.calculator.MetricValues;
import edu.s3.jqmood.calculator.metrics.MetricProperty;

public class Flexibility extends QualityMetric {

    public MetricProperty getProperty() {
        return MetricProperty.FLEXIBILITY;
    }

    @Override
    public double calculate(MetricValues mv) {

        double encapsulation = mv.get(MetricProperty.ENCAPSULATION);
        double coupling = mv.get(MetricProperty.COUPLING);
        double composition = mv.get(MetricProperty.COMPOSITION);
        double polymorphism = mv.get(MetricProperty.POLYMORPHISM);

        return 0.25 * encapsulation - 0.25 * coupling + 0.5 * composition + 0.5 * polymorphism;
    }
}