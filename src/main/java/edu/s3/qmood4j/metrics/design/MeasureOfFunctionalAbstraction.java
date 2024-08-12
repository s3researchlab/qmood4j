package edu.s3.qmood4j.metrics.design;

import edu.s3.qmood4j.metrics.DesignMetric;
import edu.s3.qmood4j.metrics.MetricProperty;
import edu.s3.qmood4j.model.ClassModel;
import edu.s3.qmood4j.model.MethodModel;
import edu.s3.qmood4j.model.ProjectModel;

/**
 * Measures of Functional Abstraction (MFA)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class MeasureOfFunctionalAbstraction extends DesignMetric {

    @Override
    public MetricProperty getProperty() {
        return MetricProperty.INHERITANCE;
    }
    
    @Override
    public double calculate(ProjectModel pm, ClassModel cm) {

        double declared = 0.0;
        double inherited = 0.0;

        for (MethodModel md : cm.getMethodModels()) {

            if (md.isStatic() || md.isAbstract()) {
                continue;
            }

            declared++;
        }
        
        for (ClassModel superClass : pm.getSuperClassModels(cm)) {

            // Super class coming from third party library is ignored.
            if (superClass == null) {
                continue;
            }

            for (MethodModel md : superClass.getMethodModels()) {

                if (md.isStatic() || md.isAbstract()) {
                    continue;
                }

                if (md.isPrivate()) {
                    continue;
                }

                inherited++;
            }
        }

        double total = inherited + declared;

        if (total == 0) {
            return 0.0;
        }

        return inherited / total;
    }
}
