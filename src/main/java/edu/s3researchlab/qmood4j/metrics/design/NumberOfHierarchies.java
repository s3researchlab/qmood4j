package edu.s3researchlab.qmood4j.metrics.design;

import java.util.Set;

import edu.s3researchlab.qmood4j.metrics.Metric;
import edu.s3researchlab.qmood4j.metrics.MetricName;
import edu.s3researchlab.qmood4j.model.ClassModel;
import edu.s3researchlab.qmood4j.model.ProjectModel;

/**
 * Number of Hierarchies (NOH)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class NumberOfHierarchies extends Metric {

    @Override
    public MetricName getName() {
        return MetricName.HIERARCHIES;
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
