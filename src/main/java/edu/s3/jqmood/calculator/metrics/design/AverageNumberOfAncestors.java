package edu.s3.jqmood.calculator.metrics.design;

import java.util.Set;

import edu.s3.jqmood.calculator.metrics.MetricProperty;
import edu.s3.jqmood.model.ClassModel;
import edu.s3.jqmood.model.ProjectModel;

/**
 * Average Number of Ancestors (ANA)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class AverageNumberOfAncestors extends DesignMetric {

    @Override
    public MetricProperty getProperty() {
        return MetricProperty.ABSTRACTION;
    }

    @Override
    public double calculate(ProjectModel pm, ClassModel cm) {

        Set<String> superClasses = pm.getSuperClasses(cm.getFullClassName());

        return (double) superClasses.size();
    }
}
