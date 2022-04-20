package com.granveaud.mysql2h2converter.parser;

import org.junit.Ignore;
import org.junit.Test;

import com.granveaud.mysql2h2converter.SQLParserManager;

import static org.junit.Assert.assertEquals;

public class BasicTest {

    private void assertStatementEquals(String str) throws ParseException {
        assertStatementEquals(str, str);
    }

    private void assertStatementEquals(String str, String compareStr) throws ParseException {
        assertEquals(compareStr, SQLParserManager.parseStatement(str).toString());
    }

    @Test
    public void testIgnoreCase() throws Exception {
        String str = "use DB";
        assertStatementEquals(str, str.toUpperCase());
    }

    @Test
    public void testUse() throws Exception {
        String str = "USE db";
        assertStatementEquals(str);
    }

    @Test
    public void testCreateDatabase() throws Exception {
        String str = "CREATE DATABASE db";
        assertStatementEquals(str);

        str = "CREATE DATABASE IF NOT EXISTS db";
        assertStatementEquals(str);
    }

    @Test
    public void testDropDatabase() throws Exception {
        String str = "DROP DATABASE db";
        assertStatementEquals(str);

        str = "DROP DATABASE IF EXISTS db";
        assertStatementEquals(str);
    }

    @Test
    public void testDropTable() throws Exception {
        String str = "DROP TABLE t1,t2";
        assertStatementEquals(str);

        str = "DROP TEMPORARY TABLE IF EXISTS t1";
        assertStatementEquals(str);
    }

    @Test
    public void testCreateTable() throws Exception {
        String str = "CREATE TABLE test (" +
                "t1 int(10) NOT NULL AUTO_INCREMENT," +
                "t2 int(10) NOT NULL," +
                "t3 varchar(55) DEFAULT ''," +
                "t4 datetime DEFAULT ''," +
                "t5 datetime(0) DEFAULT ''," +
                "PRIMARY KEY (t1)," +
                "UNIQUE KEY u1 (t1,t2)," +
                "KEY k1 (t2)," +
                "CONSTRAINT c1 FOREIGN KEY (t2) REFERENCES test2 (t2) ON DELETE CASCADE" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
        assertStatementEquals(str);

        str = "CREATE TABLE test (t1 int(10)) ENGINE=InnoDB CHARACTER SET=utf8mb4";
        assertStatementEquals(str, "CREATE TABLE test (t1 int(10)) ENGINE=InnoDB CHARACTER=utf8mb4");
    }

    @Test
    public void testAlterTable() throws Exception {
        String str = "ALTER TABLE test ADD CONSTRAINT c1 FOREIGN KEY (f1) REFERENCES test2 (t2)";
        assertStatementEquals(str);

        str = "ALTER TABLE test MODIFY c1 VARCHAR(255) NULL";
        assertStatementEquals(str);

        str = "ALTER TABLE test MODIFY COLUMN c1 VARCHAR(255) NULL";
        assertStatementEquals(str);

        str = "ALTER TABLE test MODIFY c1 VARCHAR(255) NULL FIRST";
        assertStatementEquals(str);

        str = "ALTER TABLE test MODIFY COLUMN c1 VARCHAR(255) NULL AFTER c0";
        assertStatementEquals(str);
    }

    @Test
    public void testLockUnlockTables() throws Exception {
        String str = "LOCK TABLES t1 AS t1 READ LOCAL";
        assertStatementEquals(str);

        str = "LOCK TABLES t1 AS t1 LOW_PRIORITY WRITE";
        assertStatementEquals(str);

        str = "UNLOCK TABLES";
        assertStatementEquals(str);
    }

    @Test
    public void testInsert() throws Exception {
        String str = "INSERT INTO test VALUES (1,'test',5.0,b'101010'),(2,'test',6.0,b'101010'),(0x036072ff,'test',X'036072ff',0b101010)";
        assertStatementEquals(str);
    }

    @Test
    public void testCharLiteralEscaping() throws Exception {
        String str = "INSERT INTO t1 VALUES ('this is a test '' test2 \\' \\t\\n'' \\' test3 \\'','test4','this is a test '' test5 \\' '' \\' test6 \\\\','test7')";
        assertStatementEquals(str);

        str = "INSERT INTO t1 VALUES (\"this is a test test2 \\\" \\t\\n \\\" test3 \\'\",\"test4\",\"this is a test '' test5 \\' '' \\' test6 \\\\\",\"test7\")";
        assertStatementEquals(str);
    }

    @Test
    public void testSetNames() throws ParseException {
        String str = "SET NAMES utf8mb4";
        assertStatementEquals(str, "SET NAMES=utf8mb4");
    }

    @Test
    public void testDelete() throws Exception {
        String str = "DELETE FROM test";
        assertStatementEquals(str);

        str = "DELETE FROM test WHERE t1=1";
        assertStatementEquals(str);

        str = "DELETE FROM test WHERE t1=1 AND t2='abc'";
        assertStatementEquals(str);

        str = "DELETE FROM test WHERE t1=1 OR t2=\"2\"";
        assertStatementEquals(str);
    }

    @Test
    public void testUpdate() throws ParseException {
        String str = "UPDATE test SET t1=1,t2='test',t3=5.0 WHERE AND t4='1'";
        assertStatementEquals(str);
    }

}
