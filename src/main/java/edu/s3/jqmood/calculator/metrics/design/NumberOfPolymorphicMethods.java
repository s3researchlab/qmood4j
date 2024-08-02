package edu.s3.jqmood.calculator.metrics.design;

import java.util.Set;

import edu.s3.jqmood.calculator.metrics.MetricProperty;
import edu.s3.jqmood.model.ClassModel;
import edu.s3.jqmood.model.MethodModel;
import edu.s3.jqmood.model.ProjectModel;

/**
 * Number of Polymorphic Methods (NOP)
 * 
 * @author Thiago Ferreira
 * @since July 2024
 */
public class NumberOfPolymorphicMethods extends DesignMetric {

    @Override
    public MetricProperty getProperty() {
        return MetricProperty.POLYMORPHISM;
    }
    
    @Override
    public double calculate(ProjectModel pm, ClassModel cm) {

        if (cm.isInterface()) {
            return 0.0;
        }

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
