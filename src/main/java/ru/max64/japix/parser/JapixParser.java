package ru.max64.japix.parser;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.util.List;

/**
 * Created by forester7
 */
public class JapixParser {

    public static void main(String[] args) throws IOException {
        System.out.println("Running");

        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(JapixParser.class).resolve("src/main/resources"));

        List<ParseResult<CompilationUnit>> parseResults = sourceRoot.tryToParse();
        System.out.println(parseResults);

        // Our sample is in the root of this directory, so no package name.
        CompilationUnit cu = sourceRoot.parse("", "String.java");

        // visit and print the methods names
        cu.accept(new UnitVisitor(), null);
    }

    private static class UnitVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
            System.out.print("Method: ");
            System.out.println(n.getName());
            super.visit(n, arg);
        }

        @Override
        public void visit(FieldDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
            System.out.print("Field: ");
            System.out.print("size: " + n.getVariables().size() + " ");
            System.out.println(n.getVariables().get(0).getName());

            super.visit(n, arg);
        }
    }

}
