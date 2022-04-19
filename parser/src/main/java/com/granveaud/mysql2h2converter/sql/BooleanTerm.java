package com.granveaud.mysql2h2converter.sql;

import java.util.List;

import static com.granveaud.mysql2h2converter.util.CollectionUtils.joinList;

/**
 * boolean term ::=
 * <boolean factor> ( AND <boolean factor> )*
 */
public class BooleanTerm {

    private final List<BooleanFactor> andFactors;

    public BooleanTerm(List<BooleanFactor> andFactors) {
        this.andFactors = andFactors;
    }

    @Override
    public String toString() {
        return (andFactors != null ? joinList(andFactors, " AND ") : "");
    }

}
