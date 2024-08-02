package edu.s3.jqmood.calculator.metrics.design;

import com.github.javaparser.ast.body.FieldDeclaration;

import edu.s3.jqmood.calculator.metrics.MetricProperty;
import edu.s3.jqmood.model.ClassModel;
import edu.s3.jqmood.model.ProjectModel;

/**
 * Data Access Metrics (DAM)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class DataAccessMetrics extends DesignMetric {

    @Override
    public MetricProperty getProperty() {
        return MetricProperty.ENCAPSULATION;
    }
    
    @Override
    public double calculate(ProjectModel pm, ClassModel cm) {

        double numberOfNonPublicFields = 0;

        if (cm.getNumberOfFields() == 0) {
            return 0.0;
        }

        for (FieldDeclaration fd : cm.getFields()) {

            if (fd.isPublic()) {
                continue;
            }

            numberOfNonPublicFields++;
        }

        return numberOfNonPublicFields / (double) cm.getNumberOfFields();
    }
}
