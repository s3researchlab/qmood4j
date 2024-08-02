package edu.s3.jqmood.calculator.metrics;

public enum MetricProperty {

    // Design Metrics

    DESIGN_SIZE(MetricType.DESIGN, "DSC"),

    HIERARCHIES(MetricType.DESIGN, "NOH"),

    ABSTRACTION(MetricType.DESIGN, "ANA"),

    POLYMORPHISM(MetricType.DESIGN, "NOP"),

    MESSAGING(MetricType.DESIGN, "CIS"),

    COMPLEXITY(MetricType.DESIGN, "NOM"),

    ENCAPSULATION(MetricType.DESIGN, "DAM"),

    COUPLING(MetricType.DESIGN, "DCC"),

    COHESION(MetricType.DESIGN, "CAM"),

    COMPOSITION(MetricType.DESIGN, "MOA"),

    INHERITANCE(MetricType.DESIGN, "MFA"),

    // Quality Metrics

    REUSABILITY(MetricType.QUALITY, "REUSABILITY"),

    FLEXIBILITY(MetricType.QUALITY, "FLEXIBILITY"),

    UNDERSTANDABILITY(MetricType.QUALITY, "UNDERSTANDABILITY"),

    FUNCTIONALITY(MetricType.QUALITY, "FUNCTIONALITY"),

    EXTENDIBILITY(MetricType.QUALITY, "EXTENDIBILITY"),

    EFFECTIVENESS(MetricType.QUALITY, "EFFECTIVENESS");

    private final MetricType type;

    private final String acronym;

    private MetricProperty(MetricType type, String acronym) {
        this.type = type;
        this.acronym = acronym;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public MetricType getType() {
        return this.type;
    }
}
