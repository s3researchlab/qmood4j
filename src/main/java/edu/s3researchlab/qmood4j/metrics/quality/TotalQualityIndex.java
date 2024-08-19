package edu.s3researchlab.qmood4j.metrics.quality;

import java.util.Map;

import edu.s3researchlab.qmood4j.metrics.Metric;
import edu.s3researchlab.qmood4j.metrics.MetricName;
import edu.s3researchlab.qmood4j.model.ProjectModel;

public class TotalQualityIndex extends Metric {

    public MetricName getName() {
        return MetricName.TQI;
    }

    @Override
    public double calculate(ProjectModel pm) {

        return calculate(pm.metrics);
    }
    
    public double calculate(Map<MetricName, Double> mv) {

        double reusability = mv.getOrDefault(MetricName.REUSABILITY, 0.0);
        double flexibility = mv.getOrDefault(MetricName.FLEXIBILITY, 0.0);
        double understandability = mv.getOrDefault(MetricName.UNDERSTANDABILITY, 0.0);
        double functionality = mv.getOrDefault(MetricName.FUNCTIONALITY, 0.0);
        double extendibility = mv.getOrDefault(MetricName.EXTENDIBILITY, 0.0);
        double effectiveness = mv.getOrDefault(MetricName.EFFECTIVENESS, 0.0);

        return reusability + flexibility + understandability + functionality + extendibility + effectiveness;
    }
}