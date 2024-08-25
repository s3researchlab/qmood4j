package edu.s3rl.qmood4j.utils;

import java.util.Locale;

public class OSUtils {

    private enum OSType {
        Windows, MacOS, Linux, Other
    };

    private static OSType detected = detectOS();
    
    private static OSType detectOS() {

        String name = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

        if ((name.indexOf("mac") >= 0) || (name.indexOf("darwin") >= 0)) {
            return OSType.MacOS;
        } else if (name.indexOf("win") >= 0) {
            return OSType.Windows;
        } else if (name.indexOf("nux") >= 0) {
            return OSType.Linux;
        }

        return OSType.Other;
    }

    public static boolean isMacOS() {
        return detected == OSType.MacOS;
    }
}
