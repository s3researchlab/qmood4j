package edu.s3rl.qmood4j.metrics;

import edu.s3rl.qmood4j.model.ClassModel;
import edu.s3rl.qmood4j.model.ProjectModel;

public abstract class Metric {

    public abstract MetricName getName();

    public double calculate(ProjectModel pm, ClassModel cm) {
        return 0.0;
    }

    public double calculate(ProjectModel pm) {

        double total = 0.0;

        for (ClassModel cm : pm.getClassModels().values()) {
            
            double value = calculate(pm, cm);
            
            cm.metrics.put(getName(), value);
            
            total += value;
        }

        return total / pm.getNumberOfClasses();
    }
}
