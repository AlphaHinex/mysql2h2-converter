package com.granveaud.mysql2h2converter.sql;

/**
DELETE [FROM] tbl_name
    [WHERE where_condition]
 */
public class DeleteStatement implements SqlStatement {

    private final String tableName;
    private final WhereClause whereClause;

    public DeleteStatement(String tableName, WhereClause whereClause) {
        this.tableName = tableName;
        this.whereClause = whereClause;
    }

    @Override
    public String toString() {
        return "DELETE FROM " + tableName + (whereClause != null ? " " + whereClause : "");
    }

}
