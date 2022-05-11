package com.granveaud.mysql2h2converter.parser;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasicTest {

    private void assertStatementEquals(String str) {
        assertStatementEquals(str, str);
    }

    private void assertStatementEquals(String str, String compareStr) {
        assertEquals(compareStr, SQLUtils.toSQLString(SQLUtils.parseStatements(str, DbType.mysql), DbType.mysql, new SQLUtils.FormatOption(true, false)));
    }

    @Test
    public void testIgnoreCase() {
        String str = "use DB";
        assertStatementEquals(str, str.toUpperCase());
    }

    @Test
    public void testUse() {
        String str = "USE db";
        assertStatementEquals(str);
    }

    @Test
    public void testCreateDatabase() {
        String str = "CREATE DATABASE db";
        assertStatementEquals(str);

        str = "CREATE DATABASE IF NOT EXISTS db";
        assertStatementEquals(str);
    }

    @Test
    public void testDropDatabase() {
        String str = "DROP DATABASE db";
        assertStatementEquals(str);

        str = "DROP DATABASE IF EXISTS db";
        assertStatementEquals(str);
    }

    @Test
    public void testDropTable() {
        String str = "DROP TABLE t1, t2";
        assertStatementEquals(str);

        str = "DROP TEMPORARY TABLE IF EXISTS t1";
        assertStatementEquals(str);
    }

    @Test
    public void testCreateTable() {
        String str = "CREATE TABLE test ( " +
                "t1 int(10) NOT NULL AUTO_INCREMENT, " +
                "t2 int(10) NOT NULL, " +
                "t3 varchar(55) DEFAULT '', " +
                "t4 datetime DEFAULT '', " +
                "t5 datetime(0) DEFAULT '', " +
                "PRIMARY KEY (t1) USING BTREE, " +
                "UNIQUE KEY u1 (t1, t2), " +
                "KEY k1 (t2), " +
                "CONSTRAINT c1 FOREIGN KEY (t2) REFERENCES test2 (t2) ON DELETE CASCADE, " +
                "UNIQUE INDEX `UNIQUE_NAME_NAMESPACES` (`NAME`,`NAMESPACE`) USING BTREE " +
                ") ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci";
        assertStatementEquals(str, "CREATE TABLE test ( " +
                "t1 int(10) NOT NULL AUTO_INCREMENT, " +
                "t2 int(10) NOT NULL, " +
                "t3 varchar(55) DEFAULT '', " +
                "t4 datetime DEFAULT '', " +
                "t5 datetime(0) DEFAULT '', " +
                "PRIMARY KEY USING BTREE (t1), " +
                "UNIQUE KEY u1 (t1, t2), " +
                "KEY k1 (t2), " +
                "CONSTRAINT c1 FOREIGN KEY (t2) REFERENCES test2 (t2) ON DELETE CASCADE, " +
                "UNIQUE INDEX `UNIQUE_NAME_NAMESPACES` USING BTREE (`NAME`, `NAMESPACE`) " +
                ") ENGINE = InnoDB CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci");

        str = "CREATE TABLE test (t1 int(10)) ENGINE=InnoDB CHARACTER SET=utf8mb4";
        assertStatementEquals(str, "CREATE TABLE test ( t1 int(10) ) ENGINE = InnoDB CHARACTER SET = utf8mb4");
    }

    @Test
    public void testAlterTable() {
        String str = "ALTER TABLE test ADD CONSTRAINT c1 FOREIGN KEY (f1) REFERENCES test2 (t2)";
        assertStatementEquals(str);

        str = "ALTER TABLE test MODIFY c1 VARCHAR(255) NULL";
        assertStatementEquals(str, "ALTER TABLE test MODIFY COLUMN c1 VARCHAR(255) NULL");

        str = "ALTER TABLE test MODIFY COLUMN c1 VARCHAR(255) NULL";
        assertStatementEquals(str);

        str = "ALTER TABLE test MODIFY c1 VARCHAR(255) NULL FIRST";
        assertStatementEquals(str, "ALTER TABLE test MODIFY COLUMN c1 VARCHAR(255) NULL FIRST");

        str = "ALTER TABLE test MODIFY COLUMN c1 VARCHAR(255) NULL AFTER c0";
        assertStatementEquals(str);

        str = "ALTER TABLE test MODIFY c1 VARCHAR(256) AFTER c0, MODIFY c2 VARCHAR(256)";
        assertStatementEquals(str, "ALTER TABLE test MODIFY COLUMN c1 VARCHAR(256) AFTER c0, MODIFY COLUMN c2 VARCHAR(256)");
    }

    @Test
    public void testLockUnlockTables() {
        String str = "LOCK TABLES t1 AS t READ LOCAL";
        assertStatementEquals(str, "LOCK TABLES t1 t READ LOCAL");

        str = "LOCK TABLES t1 AS t1 LOW_PRIORITY WRITE";
        assertStatementEquals(str, "LOCK TABLES t1 t1 LOW_PRIORITY WRITE");

        str = "UNLOCK TABLES";
        assertStatementEquals(str);
    }

    @Test
    public void testInsert() {
        String str = "INSERT INTO test VALUES (1, 'test', 5.0, b'101010'), (2, 'test', 6.0, b'101010'), (0x036072ff, 'test', X'036072ff', 0b101010)";
        assertStatementEquals(str, "INSERT INTO test VALUES (1, 'test', 5.0, b'101010'), (2, 'test', 6.0, b'101010'), (0x036072ff, 'test', 0x036072ff, b'101010')");
    }

    @Test
    public void testCharLiteralEscaping() {
        String str = "INSERT INTO t1 VALUES ('this is a test '' test2 \\' \\t\\n'' \\' test3 \\'','test4','this is a test '' test5 \\' '' \\' test6 \\\\','test7')";
        assertStatementEquals(str, "INSERT INTO t1 VALUES ('this is a test '' test2 '' \t\n" +
                "'' '' test3 ''', 'test4', 'this is a test '' test5 '' '' '' test6 \\\\', 'test7')");

        str = "INSERT INTO t1 VALUES (\"this is a test test2 \\\" \\t\\n \\\" test3 \\'\",\"test4\",\"this is a test '' test5 \\' '' \\' test6 \\\\\",\"test7\")";
        assertStatementEquals(str, "INSERT INTO t1 VALUES ('this is a test test2 \" \t\n" +
                " \" test3 ''', 'test4', 'this is a test '''' test5 '' '''' '' test6 \\\\', 'test7')");
    }

    @Test
    public void testSetNames() {
        String str = "SET NAMES utf8mb4";
        assertStatementEquals(str);
    }

    @Test
    public void testDelete() {
        String str = "DELETE FROM test";
        assertStatementEquals(str);

        str = "DELETE FROM test WHERE t1 = 1";
        assertStatementEquals(str);

        str = "DELETE FROM test WHERE t2 = 1 AND t1 = 'abc'";
        assertStatementEquals(str);

        str = "DELETE FROM test WHERE t1 = \"2\" OR t2 = 1";
        assertStatementEquals(str, "DELETE FROM test WHERE t1 = '2' OR t2 = 1");
    }

    @Test
    public void testUpdate() {
        String str = "UPDATE test SET t1 = 1, t2 = 'test', t3 = 5.0 WHERE t4 = 1";
        assertStatementEquals(str);

        str = "UPDATE test SET t1 = CONCAT(t1, '-\")-', \"'\", '-', t2) WHERE t4 = 1";
        assertStatementEquals(str, "UPDATE test SET t1 = CONCAT(t1, '-\")-', '''', '-', t2) WHERE t4 = 1");
    }

    @Test
    public void testWhereClause() {
        String str = "DELETE FROM test WHERE t1 IN ('1', '2', '3')";
        assertStatementEquals(str);

        str = "DELETE FROM test WHERE t1 <> '' && t2 IS NOT NULL";
        assertStatementEquals(str, "DELETE FROM test WHERE t1 <> '' AND t2 IS NOT NULL");
    }

}
