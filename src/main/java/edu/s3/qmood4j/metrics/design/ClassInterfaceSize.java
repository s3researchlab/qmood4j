package edu.s3.qmood4j.metrics.design;

import edu.s3.qmood4j.metrics.DesignMetric;
import edu.s3.qmood4j.metrics.MetricProperty;
import edu.s3.qmood4j.model.ClassModel;
import edu.s3.qmood4j.model.MethodModel;
import edu.s3.qmood4j.model.ProjectModel;

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
