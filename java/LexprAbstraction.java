/*
 * LexprAbstraction.java -- Implements the portion of the abstract syntax tree
 * that represents lambda expression abstractions.
 *
 * Michael McThrow
 * CS 152 -- Section 05
 * San José State University
 * Fall 2020
 */
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.*;

public class LexprAbstraction implements LambdaExprAST {
    private LexprVar lexprVar;
    private LambdaExprAST body;
    private String typeName;

    public LexprAbstraction(LexprVar lexprVar, LambdaExprAST body) {
        this.lexprVar = lexprVar;
        this.body = body;
        this.typeName = "abstraction";
    }

    public Set<LambdaExprAST> findFreeVars() {
        /* Get set of all free vars in the body first */
        Set<LambdaExprAST> allVars = body.findFreeVars();

        /* Then, remove lexprVar from the set */
        allVars.remove(lexprVar);

        return allVars;
    }

    /*
     * TODO: Fix this code to deal with cases where there are more than
     * 26 unique single-letter variable names in freeVars.
     */
    private LexprVar generateNewVar(Set<LambdaExprAST> freeVars) {
        LexprVar currentVar;
        Random random = new Random();

        do {
            char randomChar = (char)(random.nextInt(26) + 95);
            currentVar = new LexprVar(String.valueOf(randomChar));
        } while (freeVars.contains(currentVar));

        return currentVar;
    }

    public LambdaExprAST substitute(LambdaExprAST varExpr,
     LambdaExprAST replacementExpr) {
        if (lexprVar.equals(varExpr))
            return this;
        else {
            Set<LambdaExprAST> freeVars = replacementExpr.findFreeVars();
            if (freeVars.contains(lexprVar)) {
                LexprVar newVar = generateNewVar(freeVars);
                return new LexprAbstraction(newVar,
                 body.substitute(lexprVar, newVar).substitute(newVar,
                 replacementExpr));
            } else {
                return new LexprAbstraction(lexprVar,
                 body.substitute(varExpr, replacementExpr));
            }
        }
    }

    public boolean isBetaRedex() {
        return false;
    }

    public LambdaExprAST eval() {
        return new LexprAbstraction(lexprVar, body.eval());
    }

    public String toString() {
        return "\u03bb" + lexprVar.toString() + "." + body.toString();
    }

    public String exprType() {
        return typeName;
    }

    public LexprVar getVar() {
        return lexprVar;
    }

    public LambdaExprAST getBody() {
        return body;
    }
}

