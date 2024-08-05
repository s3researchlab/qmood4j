package edu.s3.jqmood.parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import edu.s3.jqmood.model.ProjectModel;

public class CodeParser {

    private static Logger logger = LogManager.getLogger(CodeParser.class);

    private List<Path> libraries = new ArrayList<>();

    public void addLibraries(Path library) {
        this.libraries.add(library);
    }

    public ProjectModel parse(List<Path> files) throws IOException {

        StaticJavaParser.getParserConfiguration().setCharacterEncoding(StandardCharsets.UTF_8);
        StaticJavaParser.getParserConfiguration().setSymbolResolver(getSymbolResolver());

        ProjectModel pm = new ProjectModel();

        for (int i = 0; i < files.size(); i++) {

            Path file = files.get(i);

            logger.info("Parsing ({}/{}) {}", i + 1, files.size(), file);

            CompilationUnit cu = StaticJavaParser.parse(file);

            cu.accept(new VoidVisitorAdapter<Void>() {

                @Override
                public void visit(ClassOrInterfaceDeclaration clsDecl, Void v) {

                    pm.addClassModel(clsDecl);

                    super.visit(clsDecl, null);
                }

            }, null);

        }

        logger.info("Parsing Completed. Found {} classes and {} interfaces", pm.getNumberOfClasses(),
                pm.getNumberOfInterfaces());

        return pm;
    }

    /**
     * It returns a combined type solver by taking into account new libraries (it
     * could could be folders with source codes or third-party .jar libraries)
     *
     * @param libraries should not be null
     * @return a combined type solver with folders and jar libraries
     * @throws IOException some I/O errors happen
     */
    public static CombinedTypeSolver getCombinedTypeSolver(List<Path> libraries) throws IOException {

        logger.info("Setting up symbol resolvers");

        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();

        combinedTypeSolver.add(new ReflectionTypeSolver());

        for (int i = 0; i < libraries.size(); i++) {

            Path library = libraries.get(i);

            logger.info("Adding Symbol Resolver ({}/{}): {}", i + 1, libraries.size(), library);

            if (library.toString().endsWith(".jar")) {
                combinedTypeSolver.add(new JarTypeSolver(library));
            } else {
                combinedTypeSolver.add(new JavaParserTypeSolver(library));
            }
        }

        logger.info("Symbol Resolvers: {} ", libraries.size() + 1);

        return combinedTypeSolver;
    }

    public JavaSymbolSolver getSymbolResolver() throws IOException {
        return new JavaSymbolSolver(getCombinedTypeSolver(libraries));
    }

}
