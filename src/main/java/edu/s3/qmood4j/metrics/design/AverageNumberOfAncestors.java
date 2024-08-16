package edu.s3.qmood4j.metrics.design;

import java.util.Set;

import edu.s3.qmood4j.metrics.Metric;
import edu.s3.qmood4j.metrics.MetricName;
import edu.s3.qmood4j.model.ClassModel;
import edu.s3.qmood4j.model.ProjectModel;

/**
 * Average Number of Ancestors (ANA)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class AverageNumberOfAncestors extends Metric {

    @Override
    public MetricName getName() {
        return MetricName.ABSTRACTION;
    }

    @Override
    public double calculate(ProjectModel pm, ClassModel cm) {

        Set<String> superClasses = pm.getSuperClasses(cm.getFullClassName());
       
        return (double) superClasses.size();
    }
}
