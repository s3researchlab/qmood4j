package edu.s3.jqmood.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;

public class DependencyLoader {

    /**
     * Private Constructor will prevent the instantiation of this class directly
     */
    private DependencyLoader() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    private static String checksum(Path file) throws IOException {

        ByteSource byteSource = Files.asByteSource(file.toFile());

        HashCode hc = byteSource.hash(Hashing.sha256());

        return hc.toString();
    }


    public static String similar(String a, String b) {

        String[] listA = a.trim().split(File.separator);
        String[] listB = b.trim().split(File.separator);

        List<String> output = new ArrayList<>();

        int max = Math.min(listA.length, listB.length);

        for (int i = 0; i < max; i++) {

            if (listB[i].contentEquals(listA[i])) {
                output.add(listB[i]);
            }
        }

        return String.join(File.separator, output);
    }

    public static Set<Path> load(Path folder) throws IOException {

        Set<String> checksums = new HashSet<>();
        Set<Path> libraries = new HashSet<>();

        List<Path> pomXmlFiles = FileUtils.getFilesFromFolder(folder, "pom.xml");

        for (Path jarFile : FileUtils.getFilesFromFolder(folder, ".jar")) {

            String checksum = checksum(jarFile);

            if (!checksums.contains(checksum)) {
                checksums.add(checksum);
                libraries.add(jarFile);
            }

        }

        String directory = null;

        for (Path javaFile : FileUtils.getFilesFromFolder(folder, ".java")) {

            String dir = javaFile.getParent().toString();

            if (directory == null) {
                directory = dir;
            } else {

                String same = similar(directory, dir);

                if (!directory.contentEquals(same)) {
                    directory = same;
                }

            }

            System.out.println(javaFile);
//            System.out.println(javaFile.getParent());
        }

        System.out.println("directory: " + directory);

        return libraries;
    }
}
