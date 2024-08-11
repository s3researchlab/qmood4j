package edu.s3.qmood4j.calculator.metrics.design;

import edu.s3.jqmood.model.ClassModel;
import edu.s3.jqmood.model.MethodModel;
import edu.s3.jqmood.model.ProjectModel;
import edu.s3.qmood4j.calculator.metrics.MetricProperty;

/**
 * Class Interface Size (CIS)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class ClassInterfaceSize extends DesignMetric {

    @Override
    public MetricProperty getProperty() {
        return MetricProperty.MESSAGING;
    }

    @Override
    public double calculate(ProjectModel pm, ClassModel cm) {

        double total = 0.0;

        for (MethodModel md : cm.getMethodModels()) {

            if (md.isPublic()) {
                total++;
            }
        }

        return total;
    }
}
