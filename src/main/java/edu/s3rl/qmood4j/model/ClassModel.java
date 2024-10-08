package edu.s3rl.qmood4j.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import edu.s3rl.qmood4j.metrics.MetricName;

public class ClassModel {

    private ClassOrInterfaceDeclaration classDeclaration;

    private List<VariableModel> fieldModels = new ArrayList<>();

    private List<MethodModel> methodModels = new ArrayList<>();

    private List<ConstructorDeclaration> constructors;
    
    public Map<MetricName, Double> metrics = new EnumMap<>(MetricName.class);

    public ClassModel(ClassOrInterfaceDeclaration classDeclaration) {

        this.classDeclaration = classDeclaration;

        this.constructors = classDeclaration.getConstructors();

        for (FieldDeclaration fd : classDeclaration.getFields()) {
            this.fieldModels.add(new VariableModel(fd));
        }

        for (MethodDeclaration md : classDeclaration.getMethods()) {
            this.methodModels.add(new MethodModel(md));
        }
    }

    public boolean isInterface() {
        return this.classDeclaration.isInterface();
    }

    public String getFullClassName() {
        return this.classDeclaration.getFullyQualifiedName().orElse("UNDEFINED");
    }

    public int getNumberOfMethods() {
        return this.methodModels.size();
    }

    public List<VariableModel> getFieldModels() {
        return this.fieldModels;
    }

    public List<MethodModel> getMethodModels() {
        return this.methodModels;
    }

    public List<ConstructorDeclaration> getConstructors() {
        return this.constructors;
    }

    public String toString() {
        return this.classDeclaration.toString();
    }

    public boolean hasSuperClass() {
        return this.classDeclaration.getExtendedTypes().size() != 0;
    }
}
