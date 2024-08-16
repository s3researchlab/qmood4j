package edu.s3.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3.qmood4j.metrics.Metric;
import edu.s3.qmood4j.metrics.MetricName;
import edu.s3.qmood4j.model.ProjectModel;

public class Understandability extends Metric {

    public MetricName getName() {
        return MetricName.UNDERSTANDABILITY;
    }

    @Override
    public double calculate(ProjectModel pm) {

        Map<MetricName, Double> mv = pm.getMetricValues();

        double abstraction = mv.getOrDefault(MetricName.ABSTRACTION, 0.0);
        double encapsulation = mv.getOrDefault(MetricName.ENCAPSULATION, 0.0);
        double coupling = mv.getOrDefault(MetricName.COUPLING, 0.0);
        double cohesion = mv.getOrDefault(MetricName.COHESION, 0.0);
        double polymorphism = mv.getOrDefault(MetricName.POLYMORPHISM, 0.0);
        double complexity = mv.getOrDefault(MetricName.COMPLEXITY, 0.0);
        double designSize = mv.getOrDefault(MetricName.DESIGN_SIZE, 0.0);

        return -0.33 * abstraction + 0.33 * encapsulation - 0.33 * coupling + 0.33 * cohesion - 0.33 * polymorphism - 0.33 * complexity - 0.33 * designSize;
    }
}