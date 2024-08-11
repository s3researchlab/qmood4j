package edu.s3.qmood4j.utils;

import java.io.IOException;
import java.util.List;

public class CommandUtils {

    /**
     * Private Constructor will prevent the instantiation of this class directly
     */
    private CommandUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static void run(List<String> args) {

        try {
            ProcessBuilder builder = new ProcessBuilder(args).inheritIO();
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
