package edu.s3.qmood4j.runner;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.s3.jqmood.model.ProjectModel;
import edu.s3.qmood4j.metrics.MetricValues;
import edu.s3.qmood4j.metrics.design.AverageNumberOfAncestors;
import edu.s3.qmood4j.metrics.design.ClassInterfaceSize;
import edu.s3.qmood4j.metrics.design.CohesionAmongMethodsOfClass;
import edu.s3.qmood4j.metrics.design.DataAccessMetrics;
import edu.s3.qmood4j.metrics.design.DesignMetric;
import edu.s3.qmood4j.metrics.design.DesignSizeInClasses;
import edu.s3.qmood4j.metrics.design.DirectClassCoupling;
import edu.s3.qmood4j.metrics.design.MeasureOfAggregation;
import edu.s3.qmood4j.metrics.design.MeasureOfFunctionalAbstraction;
import edu.s3.qmood4j.metrics.design.NumberOfHierarchies;
import edu.s3.qmood4j.metrics.design.NumberOfMethods;
import edu.s3.qmood4j.metrics.design.NumberOfPolymorphicMethods;
import edu.s3.qmood4j.metrics.quality.Effectiveness;
import edu.s3.qmood4j.metrics.quality.Extendibility;
import edu.s3.qmood4j.metrics.quality.Flexibility;
import edu.s3.qmood4j.metrics.quality.Functionality;
import edu.s3.qmood4j.metrics.quality.QualityMetric;
import edu.s3.qmood4j.metrics.quality.Reusability;
import edu.s3.qmood4j.metrics.quality.Understandability;
import edu.s3.qmood4j.utils.LoggerUtils;

public class CodeCalculator {

    private static Logger logger = LogManager.getLogger(CodeCalculator.class);

    private ProjectModel projectModel;

    private List<DesignMetric> designMetrics = new ArrayList<>();

    private List<QualityMetric> qualityMetrics = new ArrayList<>();

    public CodeCalculator(ProjectModel projectModel) {

        logger.info(LoggerUtils.separator);
        logger.info(LoggerUtils.green("Code Calculator"));
        logger.info(LoggerUtils.separator);
        
        this.projectModel = projectModel;

        this.designMetrics.add(new DesignSizeInClasses());
        this.designMetrics.add(new NumberOfHierarchies());
        this.designMetrics.add(new AverageNumberOfAncestors());
        this.designMetrics.add(new DataAccessMetrics());
        this.designMetrics.add(new DirectClassCoupling());
        this.designMetrics.add(new CohesionAmongMethodsOfClass());
        this.designMetrics.add(new MeasureOfAggregation());
        this.designMetrics.add(new MeasureOfFunctionalAbstraction());
        this.designMetrics.add(new NumberOfPolymorphicMethods());
        this.designMetrics.add(new ClassInterfaceSize());
        this.designMetrics.add(new NumberOfMethods());

        this.qualityMetrics.add(new Reusability());
        this.qualityMetrics.add(new Flexibility());
        this.qualityMetrics.add(new Understandability());
        this.qualityMetrics.add(new Functionality());
        this.qualityMetrics.add(new Extendibility());
        this.qualityMetrics.add(new Effectiveness());
    }

    public MetricValues calculate() {

        logger.info("Calculating {} design and {} quality metrics", designMetrics.size(), qualityMetrics.size());

        MetricValues mv = new MetricValues();

        for (DesignMetric metric : designMetrics) {
            mv.put(metric.getProperty(), metric.calculate(projectModel));
        }

        for (QualityMetric metric : qualityMetrics) {
            mv.put(metric.getProperty(), metric.calculate(mv));
        }
        
        return mv;
    }
}
