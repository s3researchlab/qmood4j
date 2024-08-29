package edu.s3rl.qmood4j.settings;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import picocli.CommandLine.IVersionProvider;

public class Settings implements IVersionProvider {

    public static long startTime = 0L;
    
    public String[] getVersion() {

        Package p = getClass().getPackage();
        String version = p.getImplementationVersion();

        return new String[] { version };
    }

    public static void init() {

        Settings.startTime = System.currentTimeMillis();
    }

    public static double getEstimatedTimeInSeconds() {
        
        return (System.currentTimeMillis() - startTime) / 1000.0;
    }

    public static String getDateTimeNow() {

        ZonedDateTime dateTimeNow = ZonedDateTime.now();

        return DateTimeFormatter.RFC_1123_DATE_TIME.format(dateTimeNow);
    }

    public static List<String> getDefaultIgnore() {

        return List.of(".*module-info.java", ".*package-info.java", ".*/target/.*", ".*/test/.*");
    }

}
