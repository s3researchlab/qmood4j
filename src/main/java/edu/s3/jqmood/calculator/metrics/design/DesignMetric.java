package edu.s3.jqmood.calculator.metrics.design;

import edu.s3.jqmood.calculator.metrics.Metric;
import edu.s3.jqmood.model.ClassModel;
import edu.s3.jqmood.model.ProjectModel;

public abstract class DesignMetric implements Metric {

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
}
