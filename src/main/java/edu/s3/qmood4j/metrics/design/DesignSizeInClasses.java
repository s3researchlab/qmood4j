package edu.s3.qmood4j.metrics.design;

import edu.s3.qmood4j.metrics.DesignMetric;
import edu.s3.qmood4j.metrics.MetricName;
import edu.s3.qmood4j.model.ProjectModel;

/**
 * Design Size in Classes (DSC)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class DesignSizeInClasses extends DesignMetric {

    @Override
    public MetricName getName() {
        return MetricName.DESIGN_SIZE;
    }

    @Override
    public double calculate(ProjectModel pm) {
        return (double) pm.getNumberOfClasses();
    }
}
