package edu.s3researchlab.qmood4j.metrics;

import java.util.Map;

import edu.s3researchlab.qmood4j.model.ClassModel;
import edu.s3researchlab.qmood4j.model.ProjectModel;

public abstract class Metric {

    public abstract MetricName getName();
    
    public double calculate(ProjectModel pm, ClassModel cm) {
        return 0.0;
    }

    public double calculate(ProjectModel pm) {

        double total = 0.0;

        for (ClassModel cm : pm.getClassModels().values()) {
            total += calculate(pm, cm);
        }

        return total / pm.getNumberOfClasses();
    }
    
    public double calculate(Map<MetricName, Double> metricValues) {
        return 0.0;
    }
}
