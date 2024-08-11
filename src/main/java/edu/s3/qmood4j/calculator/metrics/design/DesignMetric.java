package edu.s3.qmood4j.calculator.metrics.design;

import edu.s3.jqmood.model.ClassModel;
import edu.s3.jqmood.model.ProjectModel;
import edu.s3.qmood4j.calculator.metrics.Metric;

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
