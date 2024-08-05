package edu.s3.jqmood.parser.dependency;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

public interface DependencyLoader {

    public SimpleEntry<List<Path>, List<Path>> load() throws IOException;
}
