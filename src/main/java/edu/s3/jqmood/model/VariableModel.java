package edu.s3.jqmood.model;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.resolution.types.ResolvedTypeVariable;

import edu.s3.jqmood.parser.UnResolvedType;
import edu.s3.jqmood.utils.JavaParserUtils;

public class VariableModel {

	private FieldDeclaration fd;

	private ResolvedType type;

	private String fullTypeName;

	public VariableModel(ResolvedType type) {

		this.type = type;

		System.out.println("VariableModel " + type);

		if (type.isReferenceType()) {
			this.fullTypeName = type.asReferenceType().getQualifiedName();
		} else if (type.isPrimitive()) {
			this.fullTypeName = type.asPrimitive().describe();
		} else if (type.isTypeVariable()) {
			this.fullTypeName = getQualifiedName(type.asTypeVariable());
		} else if (type.isArray()) {
			this.fullTypeName = type.asArrayType().describe();
		} else if (type instanceof UnResolvedType) {
			this.fullTypeName = "jqmood.unresolved." + ((UnResolvedType) type).qualifiedName();
		} else {
			throw new RuntimeException("Type not resolved");
		}

		System.out.println(this.fullTypeName);
	}

	public boolean isPublic() {
		return this.fd.isPublic();
	}

	public VariableModel(FieldDeclaration fd) {
		this(JavaParserUtils.resolve(fd));

		this.fd = fd;
	}

	public VariableModel(Parameter parameter) {
		this(JavaParserUtils.resolve(parameter));
	}

	public String getFullTypeName() {
		return this.fullTypeName;
	}

	public boolean isPrimitive() {
		return this.type.isPrimitive();
	}

	public boolean isStandardJavaLibrary() {
		return JavaParserUtils.isStandardJavaLibrary(fullTypeName);
	}

	public boolean isUserDefinedClass() {

		if (isPrimitive()) {
			return false;
		}

		if (isStandardJavaLibrary()) {
			return false;
		}

		return true;
	}

	private String getQualifiedName(ResolvedTypeVariable resolved) {
		
//		System.out.println(resolved.isArray());
//		System.out.println(resolved.isInferenceVariable());
//		System.out.println(resolved.isNumericType());
//		System.out.println(resolved.isUnionType());
//		System.out.println(resolved.isWildcard());
//		System.out.println("<<<<<"+resolved.erasure());
		return resolved.qualifiedName();
	}

}
