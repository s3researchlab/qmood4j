package edu.s3.jqmood.utils;

import java.util.HashSet;
import java.util.Set;

import edu.s3.jqmood.model.ClassModel;
import edu.s3.jqmood.model.ProjectModel;

public class HierarchyUtils {

    /**
     * Private Constructor will prevent the instantiation of this class directly
     */
    private HierarchyUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }
    
    public static Set<ClassModel> getSuperExtendsClasses(ProjectModel pm, ClassModel cm) {

        Set<ClassModel> output = new HashSet<>();

        for (String cls : GraphUtils.getSuccessorsFrom(pm.getExtendsGraph(), cm.getFullClassName())) {
            output.add(pm.getClassModelByFullClassName(cls));
        }

        return output;
    }

    public static Set<ClassModel> getSubExtendsClasses(ProjectModel pm, ClassModel cm) {

        Set<ClassModel> output = new HashSet<>();

        for (String cls : GraphUtils.getPredecessorsFrom(pm.getExtendsGraph(), cm.getFullClassName())) {
            output.add(pm.getClassModelByFullClassName(cls));
        }

        return output;
    }
}
