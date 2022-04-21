package com.granveaud.mysql2h2converter.sql;

/**
 * expr:
 *     expr OR expr
 *   | expr || expr
 *   | expr XOR expr
 *   | expr AND expr
 *   | expr && expr
 *   | NOT expr
 *   | ! expr
 *   | boolean_primary IS [NOT] {TRUE | FALSE | UNKNOWN}
 *   | boolean_primary
 */
public class ExpressionValue implements Value {

    private final ExpressionValue leftExpr;
    /**
     * OR, ||, XOR, AND, &&, NOT, !
     */
    private final String exprOperator;
    private final ExpressionValue rightExpr;
    private final BooleanPrimary booleanPrimary;
    /**
     * IS [NOT] {TRUE | FALSE | UNKNOWN}
     */
    private final String booleanPrimaryPredicate;

    public ExpressionValue(ExpressionValue leftExpr, String exprOperator, ExpressionValue rightExpr,
                           BooleanPrimary booleanPrimary, String booleanPrimaryPredicate) {
        this.leftExpr = leftExpr;
        this.exprOperator = exprOperator;
        this.rightExpr = rightExpr;
        this.booleanPrimary = booleanPrimary;
        this.booleanPrimaryPredicate = booleanPrimaryPredicate;
    }

    @Override
    public String toString() {
        return (leftExpr != null ? leftExpr + " " : "") +
               (exprOperator != null ? exprOperator + " " : "") +
               (rightExpr != null ? rightExpr + " " : "") +
               (booleanPrimary != null ? booleanPrimary + " " : "") +
               (booleanPrimaryPredicate != null ? booleanPrimaryPredicate : "");
    }

}
