package com.granveaud.mysql2h2converter.sql;

import java.util.List;

import static com.granveaud.mysql2h2converter.util.CollectionUtils.joinList;

/**
 * where clause ::=
 * WHERE <boolean term> ( OR <boolean term> )*
 */
public class WhereClause {

    private final List<BooleanTerm> booleanTerms;

    public WhereClause(List<BooleanTerm> booleanTerms) {
        this.booleanTerms = booleanTerms;
    }

    @Override
    public String toString() {
        return "WHERE " +
                (booleanTerms != null ? joinList(booleanTerms, " OR ") : "");
    }

}
