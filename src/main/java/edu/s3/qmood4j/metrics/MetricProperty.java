package edu.s3.qmood4j.metrics;

public enum MetricProperty {

    // Design Metrics

    DESIGN_SIZE("DSC"),

    HIERARCHIES("NOH"),

    ABSTRACTION("ANA"),

    POLYMORPHISM("NOP"),

    MESSAGING("CIS"),

    COMPLEXITY("NOM"),

    ENCAPSULATION("DAM"),

    COUPLING("DCC"),

    COHESION("CAM"),

    COMPOSITION("MOA"),

    INHERITANCE("MFA"),

    // Quality Metrics

    REUSABILITY("REUSABILITY"),

    FLEXIBILITY("FLEXIBILITY"),

    UNDERSTANDABILITY("UNDERSTANDABILITY"),

    FUNCTIONALITY("FUNCTIONALITY"),

    EXTENDIBILITY("EXTENDIBILITY"),

    EFFECTIVENESS("EFFECTIVENESS");

    private final String acronym;

    private MetricProperty(String acronym) {
        this.acronym = acronym;
    }

    public String getAcronym() {
        return this.acronym;
    }
}
