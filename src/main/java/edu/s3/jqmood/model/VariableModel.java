package edu.s3.jqmood.model;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.resolution.types.ResolvedType;

import edu.s3.jqmood.parser.UnResolvedType;
import edu.s3.jqmood.utils.JavaParserUtils;

public class VariableModel {

    private ResolvedType type;

    private String fullTypeName;

    public VariableModel(ResolvedType type) {

        this.type = type;

        if (type.isReferenceType()) {
            this.fullTypeName = type.asReferenceType().getQualifiedName();
        } else if (type.isPrimitive()) {
            this.fullTypeName = type.asPrimitive().describe();
        } else if (type.isTypeVariable()) {
            this.fullTypeName = type.asTypeVariable().qualifiedName();
        } else if (type.isArray()) {
            this.fullTypeName = type.asArrayType().describe();
        } else if (type instanceof UnResolvedType) {
            this.fullTypeName = "jqmood.unresolved." + ((UnResolvedType) type).qualifiedName();
        } else {
            throw new RuntimeException("Type not resolved");
        }
        
        System.out.println(this.fullTypeName);
    }

    public VariableModel(FieldDeclaration fd) {
        this(JavaParserUtils.resolve(fd));
    }

    public VariableModel(Parameter parameter) {
        this(JavaParserUtils.resolve(parameter));
    }

    public String getFullTypeName() {
        return this.fullTypeName;
    }

    public boolean isPrimitive() {
        return this.type.isPrimitive();
    }

    public boolean isStandardJavaLibrary() {
        return JavaParserUtils.isStandardJavaLibrary(fullTypeName);
    }

    public boolean isUserDefinedClass() {

        if (isPrimitive()) {
            return false;
        }

        if (isStandardJavaLibrary()) {
            return false;
        }

        return true;
    }

}
