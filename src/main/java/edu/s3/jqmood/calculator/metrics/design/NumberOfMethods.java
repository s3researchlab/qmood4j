package edu.s3.jqmood.calculator.metrics.design;

import edu.s3.jqmood.calculator.metrics.MetricProperty;
import edu.s3.jqmood.model.ClassModel;
import edu.s3.jqmood.model.ProjectModel;

/**
 * Number Of Methods (NOM)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class NumberOfMethods extends DesignMetric {

    @Override
    public MetricProperty getProperty() {
        return MetricProperty.COMPLEXITY;
    }
    
    @Override
    public double calculate(ProjectModel pm, ClassModel cm) {
        return (double) cm.getNumberOfMethods();
    }
}
