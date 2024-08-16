package edu.s3.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3.qmood4j.metrics.Metric;
import edu.s3.qmood4j.metrics.MetricName;

public class Extendibility extends Metric {

    public MetricName getName() {
        return MetricName.EXTENDIBILITY;
    }

    @Override
    public double calculate(Map<MetricName, Double> mv) {

        double abstraction = mv.getOrDefault(MetricName.ABSTRACTION, 0.0);
        double coupling = mv.getOrDefault(MetricName.COUPLING, 0.0);
        double inheritance = mv.getOrDefault(MetricName.INHERITANCE, 0.0);
        double polymorphism = mv.getOrDefault(MetricName.POLYMORPHISM, 0.0);

        return 0.5 * abstraction - 0.5 * coupling + 0.5 * inheritance + 0.5 * polymorphism;
    }
}