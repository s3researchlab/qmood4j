package edu.s3researchlab.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3researchlab.qmood4j.metrics.Metric;
import edu.s3researchlab.qmood4j.metrics.MetricName;
import edu.s3researchlab.qmood4j.model.ProjectModel;

public class Flexibility extends Metric {

    public MetricName getName() {
        return MetricName.FLEXIBILITY;
    }

    @Override
    public double calculate(ProjectModel pm) {

        Map<MetricName, Double> mv = pm.getMetricValues();

        double encapsulation = mv.getOrDefault(MetricName.ENCAPSULATION, 0.0);
        double coupling = mv.getOrDefault(MetricName.COUPLING, 0.0);
        double composition = mv.getOrDefault(MetricName.COMPOSITION, 0.0);
        double polymorphism = mv.getOrDefault(MetricName.POLYMORPHISM, 0.0);

        return 0.25 * encapsulation - 0.25 * coupling + 0.5 * composition + 0.5 * polymorphism;
    }
}