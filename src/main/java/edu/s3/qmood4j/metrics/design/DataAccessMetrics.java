package edu.s3.qmood4j.metrics.design;

import edu.s3.qmood4j.metrics.DesignMetric;
import edu.s3.qmood4j.metrics.MetricName;
import edu.s3.qmood4j.model.ClassModel;
import edu.s3.qmood4j.model.ProjectModel;
import edu.s3.qmood4j.model.VariableModel;

/**
 * Data Access Metrics (DAM)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class DataAccessMetrics extends DesignMetric {

    @Override
    public MetricName getName() {
        return MetricName.ENCAPSULATION;
    }
    
    @Override
    public double calculate(ProjectModel pm, ClassModel cm) {

        double numberOfNonPublicFields = 0;

        if (cm.getFieldModels().size() == 0) {
            return 0.0;
        }

        for (VariableModel fd : cm.getFieldModels()) {

            if (fd.isPublic()) {
                continue;
            }

            numberOfNonPublicFields++;
        }

        return numberOfNonPublicFields / (double) cm.getFieldModels().size();
    }
}
