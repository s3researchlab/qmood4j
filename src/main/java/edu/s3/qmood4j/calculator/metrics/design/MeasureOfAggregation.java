package edu.s3.qmood4j.calculator.metrics.design;

import edu.s3.jqmood.model.ClassModel;
import edu.s3.jqmood.model.ProjectModel;
import edu.s3.jqmood.model.VariableModel;
import edu.s3.qmood4j.calculator.metrics.MetricProperty;

/**
 * Measure Of Aggregation (MOA)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class MeasureOfAggregation extends DesignMetric {

    @Override
    public MetricProperty getProperty() {
        return MetricProperty.COMPOSITION;
    }

    @Override
    public double calculate(ProjectModel pm, ClassModel cm) {

        if (cm.getFieldModels().isEmpty()) {
            return 0.0;
        }

        double total = 0.0;

        for (VariableModel field : cm.getFieldModels()) {

            if (field.isUserDefinedClass()) {
                total++;
            }
        }

        return total;
    }
}
