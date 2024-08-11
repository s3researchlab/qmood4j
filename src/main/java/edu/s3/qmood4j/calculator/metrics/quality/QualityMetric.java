package edu.s3.qmood4j.calculator.metrics.quality;

import edu.s3.qmood4j.calculator.MetricValues;
import edu.s3.qmood4j.calculator.metrics.Metric;
import edu.s3.qmood4j.calculator.metrics.MetricProperty;

public interface QualityMetric extends Metric {

    public abstract MetricProperty getProperty();

    public abstract double calculate(MetricValues mv);

}
