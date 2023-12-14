mysql2h2-converter
==================

A MySQL to H2 SQL conversion library written in Java.

Parse a MySQL dump and convert it to H2 statements either as an embedded library or as a standalone tool.

Next steps can include:
- clean up the interface to avoid Iterators
- implement other statements (SELECT, UPDATE...)
- provide samples for an on-the-fly conversion with datasource-proxy http://code.google.com/p/datasource-proxy/

Other ideas:
- look at jOOQ http://www.jooq.org/ to see if it can be used to model the DML statements and Liquibase http://www.liquibase.org/
  for the DDL part

## Building
`mvn clean package` will generate the SQL parser and build an executable JAR.

enhance branch
==============

Extend the parser to support more statements, 
see [BasicTest](https://github.com/andrewparmet/mysql2h2-converter/compare/master...AlphaHinex:enhance#diff-a52f2fd0635da9198c242298886513d39618b23312da907507eb4564f28edbcc) 
and [ConverterTest](https://github.com/andrewparmet/mysql2h2-converter/compare/master...AlphaHinex:enhance#diff-0eddb917bf0ff570f500de95243b515a22f943a118fe3aefc1edc3714ffb42db) diff for details.

Since last commit of [andrewparmet/mysql2h2-converter](https://github.com/andrewparmet/mysql2h2-converter) is 2018, 
change this library's pom to a new group and new version, 
and you can use the new one from [JitPack](https://jitpack.io/). 

Gradle example:

Step 1. Add the JitPack repository to your build file:
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency:
```gradle
dependencies {
    implementation 'com.github.alphahinex:mysql2h2-converter:0.3.1'
}
```

## Usage

As a library:
``` java
private static void convertAndCreate(Statement stmt, String sqlDump) throws SQLException, ParseException {
    List<SQLStatement> stmts = SQLUtils.parseStatements(sqlDump, DbType.mysql, SQLParserFeature.MySQLSupportStandardComment);
    
    stmt.execute(SQLUtils.toSQLString(stmts, DbType.h2));
}
```

On the command line, after `mvn package` of course:

``` bash
$ java -jar target/mysql2h2-converter-tool-0.3.1.jar demos/disconf-mysql.sql > disconf-h2.sql
```

License
=======
This code is provided under the MIT license.
