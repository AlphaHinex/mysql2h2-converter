package com.granveaud.mysql2h2converter.sql;

/**
 * boolean factor ::=
 * ( NOT )? <boolean primary>
 */
public class BooleanFactor {

    private final boolean not;
    private final BooleanPrimary booleanPrimary;

    public BooleanFactor(boolean not, BooleanPrimary booleanPrimary) {
        this.not = not;
        this.booleanPrimary = booleanPrimary;
    }

    @Override
    public String toString() {
        return not ? "NOT " : "" + booleanPrimary;
    }

}
