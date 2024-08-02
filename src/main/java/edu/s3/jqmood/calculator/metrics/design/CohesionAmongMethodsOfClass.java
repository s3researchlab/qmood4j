package edu.s3.jqmood.calculator.metrics.design;

import java.util.HashSet;
import java.util.Set;

import edu.s3.jqmood.calculator.metrics.MetricProperty;
import edu.s3.jqmood.model.ClassModel;
import edu.s3.jqmood.model.MethodModel;
import edu.s3.jqmood.model.ProjectModel;
import edu.s3.jqmood.model.VariableModel;

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
                allTypes.add(parameter.getFullTypeName());
            }
        }

        double sumIntersection = 0.0;

        for (MethodModel method : cm.getMethodModels()) {

            double intersection = 0.0;

            for (VariableModel parameter : method.getParameters()) {

                if (allTypes.contains(parameter.getFullTypeName())) {
                    intersection++;
                }
            }

            sumIntersection += intersection;
        }

        return sumIntersection;
    }
}
