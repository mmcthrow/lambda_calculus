/*
 * LambdaExprAST.java -- Provides the interface LambdaExprAST that all classes
 * that represent the abstract syntax tree implement.
 *
 * Michael McThrow
 * CS 152 -- Section 05
 * San José State University
 * Fall 2020
 */
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.*;

// --------------------
// AST Code
// --------------------
public interface LambdaExprAST {
    public LambdaExprAST substitute(LambdaExprAST varExpr,
     LambdaExprAST replacementExpr);
    public boolean isBetaRedex();
    public LambdaExprAST eval();
    public Set<LambdaExprAST> findFreeVars();
    public String toString();
    public String exprType();
}
