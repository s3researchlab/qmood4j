package edu.s3.qmood4j.utils;

import com.google.common.base.Strings;

public class LoggerUtils {

    public static final String RESET = "\u001B[0m";

    public static final String BLACK = "\u001B[30m";

    public static final String RED = "\u001B[31m";

    public static final String GREEN = "\u001B[32m";

    public static final String YELLOW = "\u001B[33m";

    public static final String BLUE = "\u001B[34m";

    public static final String PURPLE = "\u001B[35m";

    public static final String CYAN = "\u001B[36m";

    public static final String WHITE = "\u001B[37m";

    public static String separator = "------------------------------------------------------------------------";

    /**
     * Private Constructor will prevent the instantiation of this class directly
     */
    private LoggerUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static String title(String title) {

        int total = separator.length();
        int size = title.length();
        int leftSize = (total - size) / 2;
        int rightSize = (total - size) / 2;

        if (size % 2 == 1) {
            rightSize--;
        }

        String before = Strings.repeat("-", leftSize);
        String after = Strings.repeat("-", rightSize);

        return "%s%s%s".formatted(before, title, after);
    }

    public static String color(String text, String color) {

        return "%s%s%s".formatted(color, text, RESET);
    }
    
    public static String green(String text) {

        return color(text, GREEN);
    }
}
