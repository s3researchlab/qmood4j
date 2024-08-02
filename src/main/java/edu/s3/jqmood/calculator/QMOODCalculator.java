package edu.s3.jqmood.calculator;

import edu.s3.jqmood.calculator.metrics.design.AverageNumberOfAncestors;
import edu.s3.jqmood.calculator.metrics.design.ClassInterfaceSize;
import edu.s3.jqmood.calculator.metrics.design.CohesionAmongMethodsOfClass;
import edu.s3.jqmood.calculator.metrics.design.DataAccessMetrics;
import edu.s3.jqmood.calculator.metrics.design.DesignSizeInClasses;
import edu.s3.jqmood.calculator.metrics.design.DirectClassCoupling;
import edu.s3.jqmood.calculator.metrics.design.MeasureOfAggregation;
import edu.s3.jqmood.calculator.metrics.design.MeasureOfFunctionalAbstraction;
import edu.s3.jqmood.calculator.metrics.design.NumberOfHierarchies;
import edu.s3.jqmood.calculator.metrics.design.NumberOfMethods;
import edu.s3.jqmood.calculator.metrics.design.NumberOfPolymorphicMethods;
import edu.s3.jqmood.calculator.metrics.quality.Effectiveness;
import edu.s3.jqmood.calculator.metrics.quality.Extendibility;
import edu.s3.jqmood.calculator.metrics.quality.Flexibility;
import edu.s3.jqmood.calculator.metrics.quality.Functionality;
import edu.s3.jqmood.calculator.metrics.quality.Reusability;
import edu.s3.jqmood.calculator.metrics.quality.Understandability;

public class QMOODCalculator extends MetricsCalculator {

    public QMOODCalculator() {

        addDesignMetric(new DesignSizeInClasses());
        addDesignMetric(new NumberOfHierarchies());
        addDesignMetric(new AverageNumberOfAncestors());
        addDesignMetric(new DataAccessMetrics());
        addDesignMetric(new DirectClassCoupling());
        addDesignMetric(new CohesionAmongMethodsOfClass());
        addDesignMetric(new MeasureOfAggregation());
        addDesignMetric(new MeasureOfFunctionalAbstraction());
        addDesignMetric(new NumberOfPolymorphicMethods());
        addDesignMetric(new ClassInterfaceSize());
        addDesignMetric(new NumberOfMethods());

        addQualityMetric(new Reusability());
        addQualityMetric(new Flexibility());
        addQualityMetric(new Understandability());
        addQualityMetric(new Functionality());
        addQualityMetric(new Extendibility());
        addQualityMetric(new Effectiveness());
    }
}
