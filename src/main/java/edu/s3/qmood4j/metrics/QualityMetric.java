package edu.s3.qmood4j.metrics;

import java.util.Map;

public interface QualityMetric extends Metric {

    public abstract MetricProperty getProperty();

    public abstract double calculate(Map<MetricProperty, Double> mv);

}
