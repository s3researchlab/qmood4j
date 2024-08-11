package edu.s3.qmood4j.metrics.design;

import edu.s3.jqmood.model.ProjectModel;
import edu.s3.qmood4j.metrics.MetricProperty;

/**
 * Design Size in Classes (DSC)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class DesignSizeInClasses extends DesignMetric {

    @Override
    public MetricProperty getProperty() {
        return MetricProperty.DESIGN_SIZE;
    }

    @Override
    public double calculate(ProjectModel pm) {
        return (double) pm.getNumberOfClasses();
    }
}
