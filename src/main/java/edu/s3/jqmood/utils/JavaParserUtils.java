package edu.s3.jqmood.utils;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.resolution.types.ResolvedTypeVariable;

import edu.s3.jqmood.parser.UnResolvedType;

public class JavaParserUtils {

    private final static String REGEX = "^(java\\.|javax\\.*|org\\.ietf\\.|org\\.omg\\.|org\\.w3c\\.|org.xml.).*$";

    /**
     * Private Constructor will prevent the instantiation of this class directly
     */
    private JavaParserUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static boolean isStandardJavaLibrary(String fullClassName) {

        if (fullClassName.matches(REGEX)) {
            return true;
        }

        return false;
    }

    public static ResolvedType resolve(FieldDeclaration fd) {
        return resolve(fd.getElementType());
    }

    public static ResolvedType resolve(Parameter p) {
        return resolve(p.getType());
    }

    private static ResolvedType resolve(Type type) {

        try {
            return type.resolve();
        } catch (UnsolvedSymbolException ex) {
            return new UnResolvedType(type);
        }
    }

    public static String getQualifiedName(ResolvedTypeVariable resolved) {

        if (resolved.erasure().isReferenceType()) {
            return resolved.erasure().asReferenceType().getQualifiedName();
        }

        return resolved.qualifiedName();
    }
}
