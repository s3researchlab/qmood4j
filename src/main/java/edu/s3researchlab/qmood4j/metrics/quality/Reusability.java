package edu.s3researchlab.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3researchlab.qmood4j.metrics.Metric;
import edu.s3researchlab.qmood4j.metrics.MetricName;
import edu.s3researchlab.qmood4j.model.ProjectModel;

public class Reusability extends Metric {

    public MetricName getName() {
        return MetricName.REUSABILITY;
    }

    @Override
    public double calculate(ProjectModel pm) {

        Map<MetricName, Double> mv = pm.getMetricValues();

        double coupling = mv.getOrDefault(MetricName.COUPLING, 0.0);
        double cohesion = mv.getOrDefault(MetricName.COHESION, 0.0);
        double messaging = mv.getOrDefault(MetricName.MESSAGING, 0.0);
        double designSize = mv.getOrDefault(MetricName.DESIGN_SIZE, 0.0);

        return -0.25 * coupling + 0.25 * cohesion + 0.5 * messaging + 0.5 * designSize;
    }
}