package com.granveaud.mysql2h2converter.sql;

import java.util.List;

import static com.granveaud.mysql2h2converter.util.CollectionUtils.joinList;

/**
 * Only support partial operators now
 *
 * predicate:
 *     bit_expr [NOT] IN (expr [, expr] ...)
 *   | bit_expr
 *
 * bit_expr:
 *     simple_expr
 *
 * simple_expr:
 *     literal
 *   | identifier
 *
 * TODO: Support other operators
 */
public class Predicate {

    private final Value bitExpr;
    /**
     * [NOT] IN
     */
    private final String operator;
    private final List<ExpressionValue> expressions;

    public Predicate(Value bitExpr, String operator, List<ExpressionValue> expressions) {
        this.bitExpr = bitExpr;
        this.operator = operator;
        this.expressions = expressions;
    }

    @Override
    public String toString() {
        return (bitExpr != null ? bitExpr : "") +
               (operator != null ? " " + operator + "(" : "") +
               (expressions != null ? joinList(expressions, ", ") + ")" : "");
    }
}
