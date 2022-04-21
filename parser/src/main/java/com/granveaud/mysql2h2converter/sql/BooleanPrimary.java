package com.granveaud.mysql2h2converter.sql;

/**
 * Only support partial operators now
 *
 * boolean_primary:
 *     columnName IS [NOT] NULL
 *   | columnName comparison_operator expr
 *   | columnName [NOT] IN (expr [, expr] ...)
 *
 * comparison_operator: = | >= | > | <= | < | <> | !=
 *
 * TODO: Support other operators
 */
public class BooleanPrimary {

    private final String columnName;
    /**
     * IS [NOT] NULL
     */
    private final String predicate;
    /**
     * = | >= | > | <= | < | <> | !=
     */
    private final String comparisonOperator;
    private final Value value;
    private final String function;
    /**
     * [NOT] IN
     */
    private final String inPredicate;
    private final ValueList valueList;

    public BooleanPrimary(String columnName, String predicate, String comparisonOperator, Value value, String function, String inPredicate, ValueList valueList) {
        this.columnName = columnName;
        this.predicate = predicate;
        this.comparisonOperator = comparisonOperator;
        this.value = value;
        this.function = function;
        this.inPredicate = inPredicate;
        this.valueList = valueList;
    }

    @Override
    public String toString() {
        return (columnName != null && columnName.length() > 0 ? columnName : "") +
               (predicate != null && predicate.length() > 0 ? " " + predicate : "") +
               (comparisonOperator != null && comparisonOperator.length() > 0 ? comparisonOperator : "") +
               (value != null ? value : "") +
               (function != null && function.length() > 0 ? function : "") +
               (inPredicate != null && inPredicate.length() > 0 ? " " + inPredicate + " " : "") +
               (valueList != null ? valueList : "");
    }

}
