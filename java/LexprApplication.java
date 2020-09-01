/*
 * LexprApplication.java -- Implements the portion of the abstract syntax tree
 * that represents lambda expression applications.
 *
 * Michael McThrow
 * CS 152 -- Section 05
 * San José State University
 * Fall 2020
 */
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.*;

public class LexprApplication implements LambdaExprAST {
    private LambdaExprAST function;
    private LambdaExprAST argument;
    private String typeName;

    public LexprApplication(LambdaExprAST function, LambdaExprAST argument) {
        this.function = function;
        this.argument = argument;
        this.typeName = "application";
    }

    public Set<LambdaExprAST> findFreeVars() {
        Set<LambdaExprAST> functionFreeVars = function.findFreeVars();
        functionFreeVars.addAll(argument.findFreeVars());
        return functionFreeVars;
    }

    public LambdaExprAST substitute(LambdaExprAST varExpr,
     LambdaExprAST replacementExpr) {
        return new LexprApplication(
         function.substitute(varExpr, replacementExpr),
         argument.substitute(varExpr, replacementExpr));
    }

    public boolean isBetaRedex() {
        return function.exprType().equals("abstraction");
    }

    public LambdaExprAST eval() {
        if (isBetaRedex()) {
            LexprAbstraction abstraction = (LexprAbstraction)function;
            return abstraction.getBody().substitute(abstraction.getVar(),
             argument);
        }

        return new LexprApplication(function.eval(), argument.eval());
    }

    public String toString() {
        return "(" + function.toString() + " " + argument.toString() + ")";
    }

    public String exprType() {
        return typeName;
    }
}
