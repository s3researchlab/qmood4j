package edu.s3.qmood4j.metrics;

import java.util.Map;

public interface QualityMetric extends Metric {

    public abstract double calculate(Map<MetricName, Double> mv);

}
