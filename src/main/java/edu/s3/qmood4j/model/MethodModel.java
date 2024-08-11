package edu.s3.qmood4j.model;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

public class MethodModel {

    private MethodDeclaration md;

    private List<VariableModel> parameters = new ArrayList<>();

    public MethodModel(MethodDeclaration md) {

        this.md = md;

        for (Parameter parameter : this.md.getParameters()) {
            this.parameters.add(new VariableModel(parameter));
        }
    }

    public String getName() {
        return this.md.getNameAsString();
    }

    public List<VariableModel> getParameters() {
        return parameters;
    }

    public boolean isStatic() {
        return this.md.isStatic();
    }

    public boolean isAbstract() {
        return this.md.isAbstract();
    }

    public boolean isPrivate() {
        return this.md.isPrivate();
    }
    
    public boolean isPublic() {
        return this.md.isPublic();
    }

    public String getSignatureAsString() {
        return this.md.getSignature().asString();
    }
}
