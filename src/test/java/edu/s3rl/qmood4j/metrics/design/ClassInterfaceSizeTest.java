package edu.s3rl.qmood4j.metrics.design;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.s3rl.qmood4j.metrics.MetricName;
import edu.s3rl.qmood4j.runner.CodeCalculator;
import edu.s3rl.qmood4j.runner.CodeLoader;
import edu.s3rl.qmood4j.runner.CodeParser;

public class ClassInterfaceSizeTest {

    private Path base = Paths.get("src/test/resources/case-studies/class-interface-size");
    
    private double calculate(Path folder) throws IOException {
        
        
        CodeLoader loader = new CodeLoader(folder, new ArrayList<>());

        loader.load();

        CodeParser parser = new CodeParser(loader);

        parser.parse();

        CodeCalculator calculator = new CodeCalculator(parser);

        calculator.calculate();
        
        return parser.getProjectModel().metrics.get(MetricName.MESSAGING);
    }
    
    @Test
    public void testDesignA() throws IOException {
        
        assertEquals(calculate(base.resolve("design-a")), 0.0);
    }
    
    @Test
    public void testDesignB() throws IOException {
                
        assertEquals(calculate(base.resolve("design-b")), 1.0);
    }
    
   
}
