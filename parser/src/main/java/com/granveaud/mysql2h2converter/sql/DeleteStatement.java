package com.granveaud.mysql2h2converter.sql;

/**
DELETE [FROM] tbl_name
    [WHERE expressionValue]
 */
public class DeleteStatement implements SqlStatement {

    private final String tableName;
    private final ExpressionValue expressionValue;

    public DeleteStatement(String tableName, ExpressionValue expressionValue) {
        this.tableName = tableName;
        this.expressionValue = expressionValue;
    }

    @Override
    public String toString() {
        return "DELETE FROM " + tableName + (expressionValue != null ? " WHERE " + expressionValue : "");
    }

}
