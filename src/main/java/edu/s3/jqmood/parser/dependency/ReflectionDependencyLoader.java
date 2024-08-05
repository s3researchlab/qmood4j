package edu.s3.jqmood.parser.dependency;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import edu.s3.jqmood.utils.FileUtils;

public class ReflectionDependencyLoader implements DependencyLoader {

    protected Path folder;

    public ReflectionDependencyLoader(Path folder, List<String> ignoredPatterns) {
        this.folder = folder;
    }

    public SimpleEntry<List<Path>, List<Path>> load() throws IOException {

        List<Path> jarFiles = FileUtils.getFilesFromFolder(folder, ".java");
        
        String directory = null;

        for (Path javaFile : jarFiles) {

            String dir = javaFile.getParent().toString();

            if (directory == null) {
                directory = dir;
            } else {

                String same = similar(directory, dir);

                if (!directory.contentEquals(same)) {
                    directory = same;
                }

            }
        }

        System.out.println("directory: " + directory);

        //Paths.get(directory)
        return null;
    }

    private String similar(String a, String b) {

        String[] listA = a.trim().split(File.separator);
        String[] listB = b.trim().split(File.separator);

        List<String> output = new ArrayList<>();

        int max = Math.min(listA.length, listB.length);

        for (int i = 0; i < max; i++) {

            if (listA[i].contentEquals(listB[i])) {
                output.add(listA[i]);
            }
        }

        return String.join(File.separator, output);
    }

}
