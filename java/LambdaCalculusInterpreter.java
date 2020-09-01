/*
 * LambdaCalculusInterpreter.java -- Interprets lambda calculus expressions.
 *
 * Usage: java LambdaCalculusInterpreter [debug]
 * debug is an optional command-line argument that prints useful debugging
 * output such as the ANTLR parse tree, the original abstract syntax tree,
 * and each successive beta-reduction.
 *
 * Examples:
 * $ echo x | java LambdaCalculusInterpreter
 * x
 *
 * $ echo "(^x.x y)" | java LambdaCalculusInterpreter
 * y
 *
 * $ echo "(^x.x (^y.y z))" | java LambdaCalculusInterpreter debug
 * Parse tree output: (lexpr ( (lexpr ^ x . (lexpr x)) (lexpr ( (lexpr ^ y .
 * (lexpr y)) (lexpr z) )) ))
 * AST is : (λx.x (λy.y z))
 * (λy.y z)
 * z
 *
 * Michael McThrow
 * CS 152 -- Section 05
 * San José State University
 * Fall 2020
 */
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;

public class LambdaCalculusInterpreter {
    public static void main(String[] args) throws IOException {
        boolean debugOutputEnabled = args.length > 0 && args[0].equals("debug");

        BufferedReader in =
         new BufferedReader(new InputStreamReader(System.in));
        CharStream inputStream = CharStreams.fromReader(in);
        LambdaExprLexer lexer = new LambdaExprLexer(inputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        LambdaExprParser parser = new LambdaExprParser(commonTokenStream);

        ParseTree tree = parser.lexpr();
        if (debugOutputEnabled) {
            System.out.println("Parse tree output: " +
             tree.toStringTree(parser));
        }

        ASTCreatorVisitor eval = new ASTCreatorVisitor();
        LambdaExprAST AST = eval.visit(tree);
        if (debugOutputEnabled)
            System.out.println("AST is : " + AST.toString());

        // Perform successive beta-reductions until the result is no longer
        // a beta-redex.
        LambdaExprAST result = AST.eval();
        while (result.isBetaRedex()) {
            if (debugOutputEnabled)
                System.out.println(result.toString());
            result = result.eval();
        }
        System.out.println(result.toString());
    }
}
