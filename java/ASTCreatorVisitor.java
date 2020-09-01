import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.*;

// --------------------
// Parse Tree Visitor Code
// --------------------
public class ASTCreatorVisitor extends LambdaExprBaseVisitor<LambdaExprAST> {
    /** Var */
    @Override
    public LambdaExprAST visitVarExpr(LambdaExprParser.VarExprContext ctx) {
        return new LexprVar(ctx.Var().getText());
    }

    /** 'λ' Var '.' lexpr */
    @Override
    public LambdaExprAST visitAbstractionExpr(
     LambdaExprParser.AbstractionExprContext ctx) {
        return new LexprAbstraction(new LexprVar(ctx.Var().getText()),
         visit(ctx.lexpr()));
    }

    /** '(' lexpr lexpr ')' */
    @Override
    public LambdaExprAST visitApplicationExpr(
     LambdaExprParser.ApplicationExprContext ctx) {
        return new LexprApplication(visit(ctx.lexpr(0)), visit(ctx.lexpr(1)));
    }
}
