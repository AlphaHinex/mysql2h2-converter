package com.granveaud.mysql2h2converter.sql;

/**
 * Only support partial operators now
 *
 * boolean_primary:
 *     boolean_primary IS [NOT] NULL
 *   | boolean_primary comparison_operator predicate
 *   | predicate
 *
 * comparison_operator: = | >= | > | <= | < | <> | !=
 *
 * TODO: Support other operators
 */
public class BooleanPrimary {

    private final BooleanPrimary booleanPrimary;
    /**
     * IS [NOT] NULL
     */
    private final String booleanPrimaryPredicate;
    /**
     * = | >= | > | <= | < | <> | !=
     */
    private final String comparisonOperator;
    private final Predicate predicate;

    public BooleanPrimary(BooleanPrimary booleanPrimary, String booleanPrimaryPredicate, String comparisonOperator, Predicate predicate) {
        this.booleanPrimary = booleanPrimary;
        this.booleanPrimaryPredicate = booleanPrimaryPredicate;
        this.comparisonOperator = comparisonOperator;
        this.predicate = predicate;
    }

    @Override
    public String toString() {
        return (booleanPrimary != null ? booleanPrimary + " " : "") +
               (booleanPrimaryPredicate != null ? booleanPrimaryPredicate + " " : "") +
               (comparisonOperator != null ? comparisonOperator + " " : "") +
               (predicate != null ? predicate : "");
    }

}
