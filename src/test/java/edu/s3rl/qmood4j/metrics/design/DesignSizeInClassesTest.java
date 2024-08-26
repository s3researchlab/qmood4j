package edu.s3rl.qmood4j.metrics.design;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import edu.s3rl.qmood4j.metrics.MetricName;
import edu.s3rl.qmood4j.runner.CodeCalculator;
import edu.s3rl.qmood4j.runner.CodeLoader;
import edu.s3rl.qmood4j.runner.CodeParser;
import edu.s3rl.qmood4j.settings.Settings;

public class DesignSizeInClassesTest {

    private double calculate(Path folder) throws IOException {
        
        Settings.folder = folder;
        
        CodeLoader loader = new CodeLoader(folder, null);

        loader.load();

        CodeParser parser = new CodeParser(loader);

        parser.parse();

        CodeCalculator calculator = new CodeCalculator(parser);

        calculator.calculate();
        
        return parser.getProjectModel().metrics.get(MetricName.DESIGN_SIZE);
    }
    
    @Test
    public void testDesignA() throws IOException {
        
        Path folder = Paths.get("src/test/resources/case-studies/design-size-in-classes/design-a");
        
        assertEquals(calculate(folder), 1.0);
    }
    
//    @Test
//    public void testDesignB() throws IOException {
//        
//        Path folder = Paths.get("src/test/resources/case-studies/design-size-in-classes/design-b");
//        
//        assertEquals(calculate(folder), 1.0);
//    }
}
