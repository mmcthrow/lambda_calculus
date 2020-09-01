/*
 * Definition of lambda expressions
 *
 * Michael McThrow
 * CS 152 -- Section 05
 * San José State University
 * Fall 2020
 */
grammar LambdaExpr;

/* Var is uppercase because it is a lexer rule.  In order to use the
 * [a-z]+ syntax, Var must be uppercase.
 */
Var         : [a-z]+ ;

/* Skip whitespace */
WS          : [ \t\r\n]+ -> skip ;

/* Allow the use of either 'λ' or '^' for expressing abstractions since
 * the 'λ' character does not show up on American keyboards.
 */
Lambda      : 'λ'
            | '^'
            ;

/* Now defining lambda expressions.  Below is the reference BNF grammar:
 *
 * <lexpr> ::= <var>
 *           | λ<var>.<lexpr>
 *           | (<lexpr> <lexpr>)
 *
 * Labels are important for naming alternative rules.  This is important
 * for writing a Visitor class that can easily be used to build an AST
 * from the parse tree.  The labels are varExpr, abstractionExpr, and
 * applicationExpr.
 */
lexpr       : Var                   # varExpr
            | Lambda Var '.' lexpr     # abstractionExpr
            | '(' lexpr lexpr ')'   # applicationExpr
            ;
