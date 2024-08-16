package edu.s3.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3.qmood4j.metrics.MetricName;
import edu.s3.qmood4j.metrics.QualityMetric;

public class Reusability implements QualityMetric {

    public MetricName getName() {
        return MetricName.REUSABILITY;
    }

    @Override
    public double calculate(Map<MetricName, Double> mv) {

        double coupling = mv.getOrDefault(MetricName.COUPLING, 0.0);
        double cohesion = mv.getOrDefault(MetricName.COHESION, 0.0);
        double messaging = mv.getOrDefault(MetricName.MESSAGING, 0.0);
        double designSize = mv.getOrDefault(MetricName.DESIGN_SIZE, 0.0);

        return -0.25 * coupling + 0.25 * cohesion + 0.5 * messaging + 0.5 * designSize;
    }
}