package com.granveaud.mysql2h2converter.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.*;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.sql.*;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSetStatement;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import io.github.alphahinex.druid.sql.dialect.h2.visitor.H2OutputVisitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;

public class DruidConverterTest {

    private static final Logger LOGGER = LogManager.getLogger(ConverterTest.class);

     @BeforeClass
    public static void initDriver() {
        org.h2.Driver.load();
    }

    @AfterClass
    public static void cleanupDriver() {
        org.h2.Driver.unload();
    }

    private Connection connection;

    @Before
    public void initConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:test;MODE=MySQL");
    }

    @After
    public void closeConnection() throws SQLException {
        connection.close();
    }

    private void executeUpdate(String sql) throws SQLException {
        try (Statement sqlStat = connection.createStatement()) {
            sqlStat.executeUpdate(sql.toString());
        }
    }

    private List<Map<String, Object>> executeSelect(String sql) throws SQLException {
        try (Statement sqlStat = connection.createStatement()) {
            sqlStat.execute(sql.toString());
            try (ResultSet rs = sqlStat.getResultSet()) {
                ResultSetMetaData metaData = rs.getMetaData();

                List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
                while (rs.next()) {
                    Map<String, Object> record = new HashMap<String, Object>();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        record.put(metaData.getColumnName(i), rs.getObject(i));
                    }
                    result.add(record);
                }
                return result;
            }
        }
    }

    private void executeScript(Reader reader) throws IOException, SQLException {
        char[] buffer = new char[1024];
        int l = reader.read(buffer);
        StringBuilder scripts = new StringBuilder();
        while (l > 0) {
            scripts.append(buffer, 0, l);
            l = reader.read(buffer);
        }

        List<SQLStatement> statementList = SQLUtils.parseStatements(scripts.toString(), DbType.mysql);
        StringBuilder out = new StringBuilder();
        SQLASTOutputVisitor visitor = new H2OutputVisitor(out);
        SQLUtils.FormatOption option = SQLUtils.DEFAULT_FORMAT_OPTION;

        visitor.setUppCase(option.isUppCase());
        visitor.setPrettyFormat(option.isPrettyFormat());
        visitor.setParameterized(option.isParameterized());

        for (int i = 0, size = statementList.size(); i < size; i++) {
            SQLStatement stmt = statementList.get(i);

            if (i > 0) {
                SQLStatement preStmt = statementList.get(i - 1);
                if (!preStmt.isAfterSemi()) {
                    visitor.print(";");
                }

                List<String> comments = preStmt.getAfterCommentsDirect();
                if (comments != null){
                    for (int j = 0; j < comments.size(); ++j) {
                        String comment = comments.get(j);
                        if (j != 0) {
                            visitor.println();
                        }
                        visitor.printComment(comment);
                    }
                }

                visitor.println();

                if (!(stmt instanceof SQLSetStatement)) {
                    visitor.println();
                }
            }

            stmt.accept(visitor);

            if (i == size - 1) {
                List<String> comments = stmt.getAfterCommentsDirect();
                if (comments != null){
                    for (int j = 0; j < comments.size(); ++j) {
                        String comment = comments.get(j);
                        if (j != 0) {
                            visitor.println();
                        }
                        visitor.printComment(comment);
                    }
                }
            }
        }

        LOGGER.info(out.toString());
        executeUpdate(out.toString());
    }

    @Test
    public void testStringEscaping() throws Exception {
        String strValue1 = "string with escaping '' \\' \\\\ \\n \\t";
        String strResult1 = "string with escaping ' ' \\ \n \t";

        String strValue2 = "string with no escaping";
        String strResult2 = strValue2;

        String sql = "CREATE TABLE test (str1 VARCHAR(255), str2 VARCHAR(255)); \n" +
                "INSERT INTO test VALUES ('" + strValue1 + "','" + strValue2 + "');\n";

        executeScript(new StringReader(sql));

        // check inserted string
        List<Map<String, Object>> result = executeSelect("SELECT * FROM test");

        assertTrue(result.size() == 1);
        assertEquals(result.get(0).get("STR1"), strResult1);
        assertEquals(result.get(0).get("STR2"), strResult2);
    }

    @Test
    public void testScript1() throws Exception {
        loadScript("wordpress.sql");
    }

    @Test
    public void testScript2() throws Exception {
        loadScript("drupal.sql");
    }

    @Test
    public void testScript3() throws Exception {
        loadScript("xwiki.sql");
    }

    @Test
    public void testScript4() throws Exception {
        loadScript("xwiki-no-foreign-key-checks.sql");
    }

    @Test
    public void testScript5() throws Exception {
        loadScript("xwiki-sqlyog.sql");
    }

    @Test
    public void testScriptCreateTableWithOnDeleteAction() throws Exception {
        loadScript("create-table-with-constraint-on-delete.sql");
    }

    @Test
    public void testScriptExportTriggerWithDelimiter() throws Exception {
        loadScript("export-trigger-with-delimiter.sql");
    }

    @Test
    public void testScriptCreateTableWithKey() throws Exception {
        loadScript("create-table-with-key.sql");

    }

    @Test
    public void testChampScripts() throws Exception {
        loadScript("champ.sql");
    }

    private void loadScript(String s) throws Exception {
        long time0 = System.currentTimeMillis();

        LOGGER.info("Executing script " + s);
        executeScript(new InputStreamReader(getClass().getResourceAsStream("/scripts/" + s)));

        LOGGER.info("Done in " + (System.currentTimeMillis() - time0) + "ms");
    }
}
