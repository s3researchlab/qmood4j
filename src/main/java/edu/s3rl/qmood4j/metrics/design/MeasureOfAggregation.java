package edu.s3rl.qmood4j.metrics.design;

import edu.s3rl.qmood4j.metrics.Metric;
import edu.s3rl.qmood4j.metrics.MetricName;
import edu.s3rl.qmood4j.model.ClassModel;
import edu.s3rl.qmood4j.model.ProjectModel;
import edu.s3rl.qmood4j.model.VariableModel;

/**
 * Measure Of Aggregation (MOA)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class MeasureOfAggregation extends Metric {

    @Override
    public MetricName getName() {
        return MetricName.COMPOSITION;
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
