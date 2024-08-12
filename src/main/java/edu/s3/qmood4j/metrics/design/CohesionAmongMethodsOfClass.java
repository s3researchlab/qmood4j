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
 * Cohesion Among Methods Of Class (CAM)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class CohesionAmongMethodsOfClass extends DesignMetric {

    @Override
    public MetricProperty getProperty() {
        return MetricProperty.COHESION;
    }

    @Override
    public double calculate(ProjectModel pm, ClassModel cm) {

        Set<String> allTypes = new HashSet<>();

        for (MethodModel method : cm.getMethodModels()) {

            for (VariableModel parameter : method.getParameters()) {

                if (parameter.isUserDefinedClass()) {
                    allTypes.add(parameter.getFullTypeName());
                }
            }
        }

        double sumIntersection = 0.0;

        for (MethodModel method : cm.getMethodModels()) {

            Set<String> types = new HashSet<>();

            for (VariableModel parameter : method.getParameters()) {

                if (parameter.isUserDefinedClass()) {

                    if (allTypes.contains(parameter.getFullTypeName())) {
                        types.add(parameter.getFullTypeName());
                    }
                }
            }

            sumIntersection += types.size();
        }

        return sumIntersection;
    }
}
