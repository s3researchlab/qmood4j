package edu.s3.jqmood.calculator.metrics.quality;

import edu.s3.jqmood.calculator.MetricValues;
import edu.s3.jqmood.calculator.metrics.Metric;
import edu.s3.jqmood.calculator.metrics.MetricProperty;

public abstract class QualityMetric implements Metric {

    public abstract MetricProperty getProperty();

    public abstract double calculate(MetricValues mv);

}
