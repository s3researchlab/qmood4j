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

    private List<Path> libraries = new ArrayList<>();

    public void addLibraries(Path library) {
        this.libraries.add(library);
    }

    public ProjectModel parse(List<Path> files) throws IOException {

        logger.info("");
        logger.info(LoggerUtils.separator);
        logger.info(LoggerUtils.green("Code Parser"));
        logger.info(LoggerUtils.separator);

        StaticJavaParser.getParserConfiguration().setCharacterEncoding(StandardCharsets.UTF_8);
        StaticJavaParser.getParserConfiguration().setSymbolResolver(getSymbolResolver());
        
        logger.info("");
        logger.info(LoggerUtils.title("Parsing all java files"));
        logger.debug("");

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

        logger.info("");
        logger.info("Completed");
        logger.info("");
        logger.info("Class/Interface Declarations: {}, Ignored: {}", output.size(), ignored.size());

        if (!ignored.isEmpty()) {

            logger.debug("");
            logger.debug(LoggerUtils.title("Ignored Classes"));
            logger.debug("");

            for (int i = 0; i < ignored.size(); i++) {

                ClassOrInterfaceDeclaration clsDecl = ignored.get(i);

                logger.debug("({}/{}) Class '{}' from {}", i + 1, ignored.size(), clsDecl.getNameAsString(),
                        clsDecl.getData(PATH));
            }
        }
        
        logger.info("");
        logger.info(LoggerUtils.title("Resolving all java files"));
        logger.debug("");
        
        ProjectModel pm = new ProjectModel();

        for (int i = 0; i < output.size(); i++) {

            ClassOrInterfaceDeclaration clsDecl = output.get(i);

            Path file = clsDecl.getData(CodeParser.PATH);

            logger.debug("({}/{}) Resolving '{}' from {}", i + 1, output.size(), clsDecl.getNameAsString(), file);

            pm.addClassModel(clsDecl);
        }

        logger.info("");
        logger.info("Completed");
        logger.info("");
        logger.info("Classes: {}, Interfaces: {}", pm.getNumberOfClasses(), pm.getNumberOfInterfaces());
        logger.info("");
        
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
    private static CombinedTypeSolver getCombinedTypeSolver(List<Path> libraries) throws IOException {

        logger.info("");
        logger.info("Setting up symbol resolvers");
        logger.debug("");

        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();

        combinedTypeSolver.add(new ReflectionTypeSolver());

        int jarsCounter = 0;
        int foldersCounter = 0;

        for (int i = 0; i < libraries.size(); i++) {

            Path library = libraries.get(i);

            logger.debug("({}/{}) Adding resolver for {}", i + 1, libraries.size(), library);

            if (library.toString().endsWith(".jar")) {
                combinedTypeSolver.add(new JarTypeSolver(library));
                jarsCounter++;
            } else {
                combinedTypeSolver.add(new JavaParserTypeSolver(library));
                foldersCounter++;
            }
        }

        logger.info("");
        logger.info("Completed");
        logger.info("");
        logger.info("Jar Files: {}, Folders: {}", jarsCounter, foldersCounter);

        return combinedTypeSolver;
    }

    private JavaSymbolSolver getSymbolResolver() throws IOException {
        return new JavaSymbolSolver(getCombinedTypeSolver(libraries));
    }
}
