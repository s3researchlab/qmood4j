package edu.s3.qmood4j.calculator.metrics.quality;

import edu.s3.qmood4j.calculator.MetricValues;
import edu.s3.qmood4j.calculator.metrics.MetricProperty;

public class Understandability implements QualityMetric {

    public MetricProperty getProperty() {
        return MetricProperty.UNDERSTANDABILITY;
    }

    @Override
    public double calculate(MetricValues mv) {

        double abstraction = mv.get(MetricProperty.ABSTRACTION);
        double encapsulation = mv.get(MetricProperty.ENCAPSULATION);
        double coupling = mv.get(MetricProperty.COUPLING);
        double cohesion = mv.get(MetricProperty.COHESION);
        double polymorphism = mv.get(MetricProperty.POLYMORPHISM);
        double complexity = mv.get(MetricProperty.COMPLEXITY);
        double designSize = mv.get(MetricProperty.DESIGN_SIZE);

        return -0.33 * abstraction + 0.33 * encapsulation - 0.33 * coupling + 0.33 * cohesion - 0.33 * polymorphism - 0.33 * complexity - 0.33 * designSize;
    }
}