package edu.s3.qmood4j.metrics.quality;

import edu.s3.qmood4j.metrics.MetricProperty;
import edu.s3.qmood4j.metrics.MetricValues;

public class Flexibility implements QualityMetric {

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