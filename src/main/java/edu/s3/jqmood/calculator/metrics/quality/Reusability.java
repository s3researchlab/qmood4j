package edu.s3.jqmood.calculator.metrics.quality;

import edu.s3.jqmood.calculator.MetricValues;
import edu.s3.jqmood.calculator.metrics.MetricProperty;

public class Reusability extends QualityMetric {

    public MetricProperty getProperty() {
        return MetricProperty.REUSABILITY;
    }

    @Override
    public double calculate(MetricValues mv) {

        double coupling = mv.get(MetricProperty.COUPLING);
        double cohesion = mv.get(MetricProperty.COHESION);
        double messaging = mv.get(MetricProperty.MESSAGING);
        double designSize = mv.get(MetricProperty.DESIGN_SIZE);

        return -0.25 * coupling + 0.25 * cohesion + 0.5 * messaging + 0.5 * designSize;
    }
}