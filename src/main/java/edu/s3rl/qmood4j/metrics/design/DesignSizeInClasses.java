package edu.s3rl.qmood4j.metrics.design;

import edu.s3rl.qmood4j.metrics.Metric;
import edu.s3rl.qmood4j.metrics.MetricName;
import edu.s3rl.qmood4j.model.ProjectModel;

/**
 * Design Size in Classes (DSC)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class DesignSizeInClasses extends Metric {

    @Override
    public MetricName getName() {
        return MetricName.DESIGN_SIZE;
    }

    @Override
    public double calculate(ProjectModel pm) {
        return (double) pm.getNumberOfClasses();
    }
}
