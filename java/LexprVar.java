/*
 * LexprVar.java -- Implements the portion of the abstract syntax tree that
 * represents lambda expression variables.
 *
 * Michael McThrow
 * CS 152 -- Section 05
 * San José State University
 * Fall 2020
 */
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.*;

public class LexprVar implements LambdaExprAST {
    private String varName;
    private String typeName;

    public LexprVar(String varName) {
        this.varName = varName;
        this.typeName = "var";
    }

    public Set<LambdaExprAST> findFreeVars() {
        Set<LambdaExprAST> varSet = new HashSet<LambdaExprAST>();
        varSet.add(this);
        return varSet;
    }

    public LambdaExprAST substitute(LambdaExprAST varExprTmp,
     LambdaExprAST replacementExpr) {
        LexprVar varExpr = (LexprVar)varExprTmp;
        if (varExpr.varName.equals(varName))
            return replacementExpr;
        else
            return this;
    }

    public boolean isBetaRedex() {
        return false;
    }

    public LambdaExprAST eval() {
        return this;
    }

    public String toString() {
        return varName;
    }

    public String exprType() {
        return typeName;
    }

    public boolean equals(Object o) {
        LexprVar otherVar = (LexprVar)o;
        return varName.equals(otherVar.varName);
    }
}
