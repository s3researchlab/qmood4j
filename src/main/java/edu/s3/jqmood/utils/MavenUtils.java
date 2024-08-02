package edu.s3.jqmood.utils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class MavenUtils {

    /**
     * Private Constructor will prevent the instantiation of this class directly
     */
    private MavenUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    private static String getVersion(Model model, Dependency dependency) {

        checkNotNull(model);
        checkNotNull(dependency);

        String version = dependency.getVersion();

        if (version.startsWith("$")) {

            final Matcher matcher = Pattern.compile("^\\$\\{(.*)\\}$").matcher(version);

            if (matcher.find()) {

                String key = matcher.group(1);

                version = model.getProperties().getProperty(key, null);
            }
        }

        return version;
    }

    private static List<Path> convertToJarFile(Model model, Dependency dependency) {

        checkNotNull(model);
        checkNotNull(dependency);

        String userHome = System.getProperty("user.home");
        String m2 = Paths.get(".m2", "repository").toString();

        String group = dependency.getGroupId().replaceAll("\\.", File.separator);
        String artifact = dependency.getArtifactId();
        String version = getVersion(model, dependency);

        String dir = String.join(File.separator, userHome, m2, group, artifact, version);

        try {
            return FileUtils.getFilesFromFolder(Paths.get(dir), ".jar");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();

//        String jarFile = artifact + "-" + version + ".jar";
//
//        String concatenated = String.join(File.separator, userHome, m2, group, artifact, version, jarFile);
//
//        return Path.of(concatenated);
    }

    private static Model readPomFile(Path pomFile) {

        checkNotNull(pomFile);
        checkArgument(Files.exists(pomFile), pomFile + " not found");

        MavenXpp3Reader reader = new MavenXpp3Reader();

        try {
            return reader.read(new FileReader(pomFile.toFile()));
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException(e);
        }
    }

    public static void downloadDependencies(Path folder) {

        checkArgument(Files.exists(folder), folder + " not found");

        String[] args = new String[] { "/opt/homebrew/bin/mvn", "-f", folder.toString(), "dependency:resolve" };

        try {
            ProcessBuilder builder = new ProcessBuilder(args).inheritIO();
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Path> getJarFilePathsFromPomFile(Path folder) {

        checkArgument(Files.exists(folder), folder + " not found");

        Model model = readPomFile(folder.resolve("pom.xml"));

        List<Path> jarFiles = new ArrayList<>();

        for (Dependency dependency : model.getDependencies()) {

            for (Path jarFile : convertToJarFile(model, dependency)) {

                if (!Files.exists(jarFile)) {
                    downloadDependencies(folder);
                }

                jarFiles.add(jarFile);
            }
        }

        return jarFiles;
    }
}
