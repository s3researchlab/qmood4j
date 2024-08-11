package edu.s3.qmood4j.metrics.design;

import java.util.HashSet;
import java.util.Set;

import edu.s3.qmood4j.metrics.DesignMetric;
import edu.s3.qmood4j.metrics.MetricProperty;
import edu.s3.qmood4j.model.ClassModel;
import edu.s3.qmood4j.model.MethodModel;
import edu.s3.qmood4j.model.ProjectModel;
import edu.s3.qmood4j.model.VariableModel;

/**
 * Direct Class Coupling (DCC)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class DirectClassCoupling extends DesignMetric {

    @Override
    public MetricProperty getProperty() {
        return MetricProperty.COUPLING;
    }

    @Override
    public double calculate(ProjectModel pm, ClassModel cm) {

        Set<String> classes = new HashSet<>();

        for (VariableModel field : cm.getFieldModels()) {

            if (field.isUserDefinedClass()) {
                classes.add(field.getFullTypeName());
            }
        }

        for (MethodModel method : cm.getMethodModels()) {

            for (VariableModel parameter : method.getParameters()) {

                if (parameter.isUserDefinedClass()) {
                    classes.add(parameter.getFullTypeName());
                }
            }
        }

        return (double) classes.size();
    }
}
