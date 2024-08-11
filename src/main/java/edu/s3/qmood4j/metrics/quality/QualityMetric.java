package edu.s3.qmood4j.metrics.quality;

import edu.s3.qmood4j.metrics.Metric;
import edu.s3.qmood4j.metrics.MetricProperty;
import edu.s3.qmood4j.metrics.MetricValues;

public interface QualityMetric extends Metric {

    public abstract MetricProperty getProperty();

    public abstract double calculate(MetricValues mv);

}
