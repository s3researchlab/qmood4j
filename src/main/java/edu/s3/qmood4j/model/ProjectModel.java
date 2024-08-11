package edu.s3.qmood4j.model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import edu.s3.qmood4j.utils.GraphUtils;

public class ProjectModel {

    public Path path;

    private Map<String, ClassModel> classModels = new HashMap<>();

    private Map<String, ClassModel> interfaceModels = new HashMap<>();

    private MutableGraph<String> extendsGraph = GraphBuilder.directed().build();

    public void addClassModel(ClassOrInterfaceDeclaration clsDecl) {
        
        String fullClassName = clsDecl.getFullyQualifiedName().orElseThrow(() -> new RuntimeException("Class not found"));

        GraphUtils.addNodeIfNotExist(extendsGraph, fullClassName);

        for (ClassOrInterfaceType cit : clsDecl.getExtendedTypes()) {

            try {

                String extendedFullClassName = cit.resolve().asReferenceType().getQualifiedName();

                GraphUtils.addNodeIfNotExist(extendsGraph, extendedFullClassName);
                GraphUtils.addEdgeIfNotExist(extendsGraph, fullClassName, extendedFullClassName);

            } catch (Exception ex) {
                System.out.println("oiii");
                // Ignore classes from third-party libraries
            }
        }

        ClassModel clsModel = new ClassModel(clsDecl);

        if (clsDecl.isInterface()) {
            this.interfaceModels.put(fullClassName, clsModel);

        } else {
            this.classModels.put(fullClassName, clsModel);
        }
    }

    /**
     * @return the number of classes, excluding interfaces
     */
    public int getNumberOfClasses() {
        return this.classModels.size();
    }

    /**
     * @return the number of interfaces, excluding classes
     */
    public int getNumberOfInterfaces() {
        return this.interfaceModels.size();
    }

    public Map<String, ClassModel> getClassModels() {
        return classModels;
    }

    public ClassModel getClassModelByFullClassName(String fullClassName) {
        return this.classModels.get(fullClassName);
    }

    public MutableGraph<String> getExtendsGraph() {
        return this.extendsGraph;
    }

    public Set<String> getSubClasses(String fullClassName) {

        Set<String> output = new HashSet<>();

        for (String cls : GraphUtils.getPredecessorsFrom(extendsGraph, fullClassName)) {
            output.add(cls);
        }

        return output;
    }

    public Set<String> getSuperClasses(String fullClassName) {

        Set<String> output = new HashSet<>();

        for (String cls : GraphUtils.getSuccessorsFrom(extendsGraph, fullClassName)) {
            output.add(cls);
        }

        return output;
    }

    public Set<ClassModel> getSuperClassModels(ClassModel cm) {

        return this.getSuperClasses(cm.getFullClassName()).stream().map(e -> getClassModelByFullClassName(e))
                .collect(Collectors.toSet());
    }

    public Set<ClassModel> getSubClassModels(ClassModel cm) {

        return this.getSubClasses(cm.getFullClassName()).stream().map(e -> getClassModelByFullClassName(e))
                .collect(Collectors.toSet());
    }
}
