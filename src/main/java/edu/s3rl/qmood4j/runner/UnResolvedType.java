package edu.s3rl.qmood4j.runner;

import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.types.ResolvedType;

public class UnResolvedType implements ResolvedType {

    private Type type;

    public UnResolvedType(Type type) {
        this.type = type;
    }

    public String qualifiedName() {
        return this.type.asString();
    }

    @Override
    public String describe() {
        return type.toDescriptor();
    }

    @Override
    public boolean isAssignableBy(ResolvedType other) {
        throw new UnsupportedOperationException("Operation not supported with UnResolvedType");
    }

}
