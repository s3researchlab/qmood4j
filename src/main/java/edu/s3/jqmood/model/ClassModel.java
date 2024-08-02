package edu.s3.jqmood.model;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class ClassModel {

    private ClassOrInterfaceDeclaration classDeclaration;

//    private List<FieldDeclaration> fields;

    private List<VariableModel> fieldModels = new ArrayList<>();

    private List<MethodDeclaration> methods;

    private List<MethodModel> methodModels = new ArrayList<>();

    private List<ConstructorDeclaration> constructors;

    public ClassModel(ClassOrInterfaceDeclaration classDeclaration) {

        this.classDeclaration = classDeclaration;

        this.methods = classDeclaration.getMethods();
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

    public ClassOrInterfaceDeclaration getClassDeclaration() {
        return classDeclaration;
    }

    public String getFullClassName() {
        return this.classDeclaration.getFullyQualifiedName().orElse("UNDEFINED");
    }

    public int getNumberOfMethods() {
        return this.methods.size();
    }

    public List<MethodDeclaration> getMethods() {
        return this.methods;
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
