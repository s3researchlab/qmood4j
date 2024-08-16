package edu.s3.qmood4j.runner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.DataKey;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import edu.s3.qmood4j.model.ProjectModel;
import edu.s3.qmood4j.utils.LoggerUtils;

public class CodeParser {

    private static Logger logger = LogManager.getLogger(CodeParser.class);

    public static final DataKey<Path> PATH = new DataKey<>() {
    };

    private List<Path> files;

    private List<Path> libraries = new ArrayList<>();

    private ProjectModel pm = new ProjectModel();

    public CodeParser(List<Path> files) {
        this.files = files;
    }

    public void addLibraries(Path library) {
        this.libraries.add(library);
    }

    public ProjectModel getProjectModel() {
        return this.pm;
    }

    public void parse() throws IOException {

        StaticJavaParser.getParserConfiguration().setCharacterEncoding(StandardCharsets.UTF_8);
        StaticJavaParser.getParserConfiguration().setSymbolResolver(getSymbolResolver());

        LoggerUtils.section("Parsing java files");
        
        List<ClassOrInterfaceDeclaration> output = new ArrayList<>();
        List<ClassOrInterfaceDeclaration> ignored = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {

            Path file = files.get(i);

            logger.debug("({}/{}) Parsing {}", i + 1, files.size(), file);

            CompilationUnit cu = StaticJavaParser.parse(file);

            cu.accept(new VoidVisitorAdapter<Void>() {

                @Override
                public void visit(ClassOrInterfaceDeclaration clsDecl, Void v) {

                    clsDecl.setData(PATH, file);

                    if (clsDecl.isTopLevelType()) {
                        output.add(clsDecl);
                    } else {

                        Optional<Node> parent = clsDecl.getParentNode();

                        if (parent.isPresent()) {

                            if (parent.get() instanceof LocalClassDeclarationStmt) {
                                ignored.add(clsDecl);
                            } else {
                                output.add(clsDecl);
                            }
                        }
                    }

                    super.visit(clsDecl, null);
                }

            }, null);
        }

        logger.debug("Completed. Class/Interface Declarations: {}, Ignored: {}", output.size(), ignored.size());
        
        LoggerUtils.section("Ignored Classes");
        
        if (ignored.isEmpty()) {
            logger.debug("No Class/Interface ingnored");
        } else {
            for (int i = 0; i < ignored.size(); i++) {

                ClassOrInterfaceDeclaration clsDecl = ignored.get(i);

                logger.debug("({}/{}) Class '{}' from {}", i + 1, ignored.size(), clsDecl.getNameAsString(),
                        clsDecl.getData(PATH));
            }
        }

        LoggerUtils.section("Resolving java files");
        
        for (int i = 0; i < output.size(); i++) {

            ClassOrInterfaceDeclaration clsDecl = output.get(i);

            Path file = clsDecl.getData(CodeParser.PATH);

            logger.debug("({}/{}) Resolving '{}' from {}", i + 1, output.size(), clsDecl.getNameAsString(), file);

            this.pm.addClassModel(clsDecl);
        }

        logger.debug("Completed. Classes: {}, Interfaces: {}", pm.getNumberOfClasses(), pm.getNumberOfInterfaces());
    }

    /**
     * It returns a combined type solver by taking into account new libraries (it
     * could could be folders with source codes or third-party .jar libraries)
     *
     * @param libraries should not be null
     * @return a combined type solver with folders and jar libraries
     * @throws IOException some I/O errors happen
     */
    private JavaSymbolSolver getSymbolResolver() throws IOException {

        LoggerUtils.section("Setting up symbol resolvers");
        
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();

        combinedTypeSolver.add(new ReflectionTypeSolver());

        int jarsCounter = 0;
        int foldersCounter = 0;

        for (int i = 0; i < this.libraries.size(); i++) {

            Path library = this.libraries.get(i);

            logger.debug("({}/{}) Adding resolver for {}", i + 1, this.libraries.size(), library);

            if (library.toString().endsWith(".jar")) {
                combinedTypeSolver.add(new JarTypeSolver(library));
                jarsCounter++;
            } else {
                combinedTypeSolver.add(new JavaParserTypeSolver(library));
                foldersCounter++;
            }
        }

        logger.debug("Completed. Jar Files: {}, Folders: {}", jarsCounter, foldersCounter);

        return new JavaSymbolSolver(combinedTypeSolver);
    }
}
