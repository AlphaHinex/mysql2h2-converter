package com.granveaud.mysql2h2converter.sql;

/**
 * expr:
 *     boolean_primary
 *   | boolean_primary OR boolean_primary
 *   | boolean_primary || boolean_primary
 *   | boolean_primary XOR boolean_primary
 *   | boolean_primary AND boolean_primary
 *   | boolean_primary && boolean_primary
 */
public class ExpressionValue implements Value {

    private final BooleanPrimary leftExpr;
    /**
     * OR, ||, XOR, AND, &&, NOT, !
     */
    private final String exprOperator;
    private final BooleanPrimary rightExpr;

    public ExpressionValue(BooleanPrimary leftExpr, String exprOperator, BooleanPrimary rightExpr) {
        this.leftExpr = leftExpr;
        this.exprOperator = exprOperator;
        this.rightExpr = rightExpr;
    }

    @Override
    public String toString() {
        return (leftExpr != null ? leftExpr : "") +
               (exprOperator != null && exprOperator.length() > 0 ? " " + exprOperator + " " : "") +
               (rightExpr != null ? rightExpr : "");
    }

}
