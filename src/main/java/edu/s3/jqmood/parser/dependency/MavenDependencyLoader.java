package edu.s3.jqmood.parser.dependency;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import edu.s3.jqmood.utils.FileUtils;

public class MavenDependencyLoader extends ReflectionDependencyLoader {

    private static Logger logger = LogManager.getLogger(MavenDependencyLoader.class);

    private List<String> ignoredPatterns = new ArrayList<>();

    public MavenDependencyLoader(Path folder, List<String> ignoredPatterns) {
        super(folder, ignoredPatterns);

        this.ignoredPatterns = ignoredPatterns;
    }

    private List<Path> getTargetFolders(Path folder) {

        Model model = readPomFile(folder.resolve("pom.xml"));
        
//        System.out.println(model.getParent().getRelativePath());
        
//        System.out.println(model.getBuild());

        List<Path> folders = new ArrayList<>();

        folders.add(folder);

        if (model.getPackaging().equalsIgnoreCase("pom")) {

            for (String module : model.getModules()) {
//                folders.addAll(getTargetFolders(folder.resolve(module)));
            }

        }

        return folders;
    }

    @Override
    public SimpleEntry<List<Path>, List<Path>> load() throws IOException {

        List<Path> targetFolders = getTargetFolders(folder);

        List<Path> javaFiles = new ArrayList<>();
        List<Path> dependencies = new ArrayList<>();

        for (Path folder : targetFolders) {

            System.out.println(">>>>>>>>"+folder);

            List<Path> files = FileUtils.getFilesFromFolder(folder, ignoredPatterns, ".java");
//
            javaFiles.addAll(files);
            dependencies.addAll(loadMavenDependencies(folder));
//
//            if (!files.isEmpty()) {
//                dependencies.add(loadReflectionDependencies(files));
//            }
        }

//        List
        System.out.println("Dependencies");
        dependencies.stream().forEach(System.out::println);
        System.out.println("Files");
        javaFiles.stream().forEach(System.out::println);

        return null;
    }

    public Path loadReflectionDependencies(List<Path> javaFiles) {

        String directory = null;

        for (Path javaFile : javaFiles) {

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

        return Paths.get(directory);
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

    public List<Path> loadMavenDependencies(Path folder) throws IOException {

        Path jarsFolder = folder.resolve("target", "dependency");

        if (!Files.exists(jarsFolder)) {
            downloadDependencies(folder);
        }

        return FileUtils.getFilesFromFolder(jarsFolder, ".jar");
    }

    private static void downloadDependencies(Path folder) {

        checkArgument(Files.exists(folder), folder + " not found");

//        List<String> ar = List.of("/opt/homebrew/bin/mvn", "-f", folder.toString(), "dependency:copy-dependencies");
        
        List<String> ar = List.of("/opt/homebrew/bin/mvn", "-f", folder.toString(), "install");


        try {
            ProcessBuilder builder = new ProcessBuilder(ar).inheritIO();
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Model readPomFile(Path pomFile) {

        checkNotNull(pomFile);
        checkArgument(Files.exists(pomFile), pomFile + " not found");

        MavenXpp3Reader reader = new MavenXpp3Reader();

        try {

            Model model = reader.read(new FileReader(pomFile.toFile()));

            model.setPomFile(pomFile.toFile());

            return model;
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException(e);
        }
    }
}
