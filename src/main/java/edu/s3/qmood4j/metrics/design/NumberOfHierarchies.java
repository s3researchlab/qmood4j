package edu.s3.qmood4j.metrics.design;

import java.util.Set;

import edu.s3.qmood4j.metrics.DesignMetric;
import edu.s3.qmood4j.metrics.MetricProperty;
import edu.s3.qmood4j.model.ClassModel;
import edu.s3.qmood4j.model.ProjectModel;

/**
 * Number of Hierarchies (NOH)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class NumberOfHierarchies extends DesignMetric {

    @Override
    public MetricProperty getProperty() {
        return MetricProperty.HIERARCHIES;
    }

    @Override
    public double calculate(ProjectModel pm) {

        double total = 0;

        for (ClassModel cm : pm.getClassModels().values()) {

            // Do not count classes with super classes
            if (cm.hasSuperClass()) {
                continue;
            }

            Set<String> subClassModels = pm.getSubClasses(cm.getFullClassName());

            // Only count if the class has children
            if (!subClassModels.isEmpty()) {
                total++;
            }
        }

        return total;
    }
}
