package edu.s3.qmood4j.metrics;

public enum MetricName {

    DESIGN_SIZE("qmood.design.design_size"),

    HIERARCHIES("qmood.design.hierarchies"),

    ABSTRACTION("qmood.design.abstraction"),

    POLYMORPHISM("qmood.design.polymorphism"),

    MESSAGING("qmood.design.messaging"),

    COMPLEXITY("qmood.design.complexity"),

    ENCAPSULATION("qmood.design.encapsulation"),

    COUPLING("qmood.design.coupling"),

    COHESION("qmood.design.cohesion"),

    COMPOSITION("qmood.design.composition"),

    INHERITANCE("qmood.design.inheritance"),

    REUSABILITY("qmood.quality.reusability"),

    FLEXIBILITY("qmood.quality.flexibility"),

    UNDERSTANDABILITY("qmood.quality.understandability"),

    FUNCTIONALITY("qmood.quality.functionality"),

    EXTENDIBILITY("qmood.quality.extendibility"),

    EFFECTIVENESS("qmood.quality.effectiveness");

    private final String key;

    private MetricName(String acronym) {
        this.key = acronym;
    }

    public String getKey() {
        return this.key;
    }
}
