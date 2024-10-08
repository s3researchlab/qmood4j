package edu.s3rl.qmood4j.metrics.design;

import java.util.HashSet;
import java.util.Set;

import edu.s3rl.qmood4j.metrics.Metric;
import edu.s3rl.qmood4j.metrics.MetricName;
import edu.s3rl.qmood4j.model.ClassModel;
import edu.s3rl.qmood4j.model.MethodModel;
import edu.s3rl.qmood4j.model.ProjectModel;
import edu.s3rl.qmood4j.model.VariableModel;

/**
 * Direct Class Coupling (DCC)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class DirectClassCoupling extends Metric {

    @Override
    public MetricName getName() {
        return MetricName.COUPLING;
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
