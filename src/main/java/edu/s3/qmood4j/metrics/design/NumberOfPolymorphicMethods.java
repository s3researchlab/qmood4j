package edu.s3.qmood4j.metrics.design;

import java.util.Set;

import edu.s3.qmood4j.metrics.DesignMetric;
import edu.s3.qmood4j.metrics.MetricName;
import edu.s3.qmood4j.model.ClassModel;
import edu.s3.qmood4j.model.MethodModel;
import edu.s3.qmood4j.model.ProjectModel;

/**
 * Number of Polymorphic Methods (NOP)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class NumberOfPolymorphicMethods extends DesignMetric {

    @Override
    public MetricName getName() {
        return MetricName.POLYMORPHISM;
    }

    @Override
    public double calculate(ProjectModel pm, ClassModel cm) {

        double total = 0.0;

        Set<ClassModel> subClasses = pm.getSubClassModels(cm);

        for (MethodModel md : cm.getMethodModels()) {

            if (isPolymorphic(subClasses, md)) {
                total++;
            }
        }

        return total;
    }

    private boolean isPolymorphic(Set<ClassModel> subClasses, MethodModel targetMd) {

        for (ClassModel subClass : subClasses) {

            for (MethodModel md : subClass.getMethodModels()) {

                if (sameSignature(md, targetMd)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean sameSignature(MethodModel md1, MethodModel md2) {
        return md1.getSignatureAsString().contentEquals(md2.getSignatureAsString());
    }
}
