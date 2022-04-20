package com.granveaud.mysql2h2converter.sql;

/**
 * Only Support simple equal operator now
 *
 * TODO
 *
 * boolean primary ::=
 * ( common value expression ( between predicate | match predicate | like regex predicate | in predicate | is null predicate | quantified comparison predicate | comparison predicate )? )
 *
 * exists predicate
 *
 * xml query
 */
public class BooleanPrimary {

    private final String columnName;

    private final Value value;

    public BooleanPrimary(String columnName, Value value) {
        this.columnName = columnName;
        this.value = value;
    }

    @Override
    public String toString() {
        return columnName + "=" + value;
    }

}
