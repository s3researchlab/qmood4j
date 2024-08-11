package edu.s3.qmood4j.calculator.metrics.quality;

import edu.s3.qmood4j.calculator.MetricValues;
import edu.s3.qmood4j.calculator.metrics.MetricProperty;

public class Functionality implements QualityMetric {

    public MetricProperty getProperty() {
        return MetricProperty.FUNCTIONALITY;
    }

    @Override
    public double calculate(MetricValues mv) {

        double cohesion = mv.get(MetricProperty.COHESION);
        double polymorphism = mv.get(MetricProperty.POLYMORPHISM);
        double messaging = mv.get(MetricProperty.MESSAGING);
        double hierarchies = mv.get(MetricProperty.HIERARCHIES);
        double designSize = mv.get(MetricProperty.DESIGN_SIZE);

        return 0.12 * cohesion + 0.22 * polymorphism + 0.22 * messaging + 0.22 * designSize + 0.22 * hierarchies;
    }
}