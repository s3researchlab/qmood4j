package edu.s3rl.qmood4j.metrics.design;

import edu.s3rl.qmood4j.metrics.Metric;
import edu.s3rl.qmood4j.metrics.MetricName;
import edu.s3rl.qmood4j.model.ClassModel;
import edu.s3rl.qmood4j.model.ProjectModel;

/**
 * Number Of Methods (NOM)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class NumberOfMethods extends Metric {

    @Override
    public MetricName getName() {
        return MetricName.COMPLEXITY;
    }
    
    @Override
    public double calculate(ProjectModel pm, ClassModel cm) {
        return (double) cm.getNumberOfMethods();
    }
}
