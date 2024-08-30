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

public class DataAccessMetricTest {

    private Path base = Paths.get("src/test/resources/case-studies/data-access-metric");
    
    private double calculate(Path folder) throws IOException {
        
        
        CodeLoader loader = new CodeLoader(folder, new ArrayList<>());

        loader.load();

        CodeParser parser = new CodeParser(loader);

        parser.parse();

        CodeCalculator calculator = new CodeCalculator(parser);

        calculator.calculate();
        
        return parser.getProjectModel().metrics.get(MetricName.ENCAPSULATION);
    }
    
    @Test
    public void testDesignA() throws IOException {
        
        assertEquals(calculate(base.resolve("design-a")), 0.0);
    }
    
    @Test
    public void testDesignB() throws IOException {
                
        assertEquals(calculate(base.resolve("design-b")), 0.0);
    }
    
    @Test
    public void testDesignC() throws IOException {
                
        assertEquals(calculate(base.resolve("design-c")), 1.0);
    }
    
    @Test
    public void testDesignD() throws IOException {
                
        assertEquals(calculate(base.resolve("design-d")), 0.75);
    }
    
   
}
