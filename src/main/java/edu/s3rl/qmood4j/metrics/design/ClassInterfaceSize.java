package edu.s3rl.qmood4j.metrics.design;

import edu.s3rl.qmood4j.metrics.Metric;
import edu.s3rl.qmood4j.metrics.MetricName;
import edu.s3rl.qmood4j.model.ClassModel;
import edu.s3rl.qmood4j.model.MethodModel;
import edu.s3rl.qmood4j.model.ProjectModel;

/**
 * Class Interface Size (CIS)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class ClassInterfaceSize extends Metric {

    @Override
    public MetricName getName() {
        return MetricName.MESSAGING;
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
