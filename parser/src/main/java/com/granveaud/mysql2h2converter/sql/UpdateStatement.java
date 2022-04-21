package com.granveaud.mysql2h2converter.sql;

import java.util.List;

import static com.granveaud.mysql2h2converter.util.CollectionUtils.joinList;

public class UpdateStatement implements SqlStatement {

    private final String tableName;
    private final List<Assignment> assignments;
    private final ExpressionValue expressionValue;

    public UpdateStatement(String tableName, List<Assignment> assignments, ExpressionValue expressionValue) {
        this.tableName = tableName;
        this.assignments = assignments;
        this.expressionValue = expressionValue;
    }

    @Override
    public String toString() {
        return "UPDATE " + tableName +
                 " SET " + joinList(assignments, ",") +
                (expressionValue != null ? " WHERE " + expressionValue : "");
    }

}
