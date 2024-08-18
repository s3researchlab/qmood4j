package edu.s3researchlab.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3researchlab.qmood4j.metrics.Metric;
import edu.s3researchlab.qmood4j.metrics.MetricName;
import edu.s3researchlab.qmood4j.model.ProjectModel;

public class Effectiveness extends Metric {

    public MetricName getName() {
        return MetricName.EFFECTIVENESS;
    }
    
    @Override
    public double calculate(ProjectModel pm) {

        Map<MetricName, Double> mv = pm.getMetricValues();
        
        double abstraction = mv.getOrDefault(MetricName.ABSTRACTION, 0.0);
        double encapsulation = mv.getOrDefault(MetricName.ENCAPSULATION, 0.0);
        double composition = mv.getOrDefault(MetricName.COMPOSITION, 0.0);
        double inheritance = mv.getOrDefault(MetricName.INHERITANCE, 0.0);
        double polymorphism = mv.getOrDefault(MetricName.POLYMORPHISM, 0.0);

        return 0.2 * abstraction + 0.2 * encapsulation + 0.2 * composition + 0.2 * inheritance + 0.2 * polymorphism;
    }
}