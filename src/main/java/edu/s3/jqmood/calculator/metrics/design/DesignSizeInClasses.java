package edu.s3.jqmood.calculator.metrics.design;

import edu.s3.jqmood.calculator.metrics.MetricProperty;
import edu.s3.jqmood.model.ProjectModel;

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
