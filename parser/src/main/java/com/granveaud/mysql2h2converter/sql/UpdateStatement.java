package com.granveaud.mysql2h2converter.sql;

import java.util.List;

import static com.granveaud.mysql2h2converter.util.CollectionUtils.joinList;

public class UpdateStatement implements SqlStatement {

    private final String tableName;
    private final List<Assignment> assignments;
    private final WhereClause whereClause;

    public UpdateStatement(String tableName, List<Assignment> assignments, WhereClause whereClause) {
        this.tableName = tableName;
        this.assignments = assignments;
        this.whereClause = whereClause;
    }

    @Override
    public String toString() {
        return "UPDATE " + tableName +
                 " SET " + joinList(assignments, ",") +
                (whereClause != null ? " " + whereClause : "");
    }

}
