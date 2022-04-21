package com.granveaud.mysql2h2converter.sql;

/**
 * expr:
 *     boolean_primary OR boolean_primary
 *   | boolean_primary || boolean_primary
 *   | boolean_primary XOR boolean_primary
 *   | boolean_primary AND boolean_primary
 *   | boolean_primary && boolean_primary
 *   | NOT boolean_primary
 *   | ! boolean_primary
 *   | boolean_primary IS [NOT] {TRUE | FALSE | UNKNOWN}
 *   | boolean_primary
 */
public class ExpressionValue implements Value {

    private final BooleanPrimary leftExpr;
    /**
     * OR, ||, XOR, AND, &&, NOT, !
     */
    private final String exprOperator;
    private final BooleanPrimary rightExpr;
    private final BooleanPrimary booleanPrimary;
    /**
     * IS [NOT] {TRUE | FALSE | UNKNOWN}
     */
    private final String booleanPrimaryPredicate;

    public ExpressionValue(BooleanPrimary leftExpr, String exprOperator, BooleanPrimary rightExpr,
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
