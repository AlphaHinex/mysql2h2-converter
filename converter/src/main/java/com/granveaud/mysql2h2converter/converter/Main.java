package com.granveaud.mysql2h2converter.converter;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class Main {
    final static private Charset INPUT_CHARSET = Charset.forName("UTF-8");

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: input_file1 input_file2...");
            System.exit(1);
        }

        for (String str : args) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(readFile(str), INPUT_CHARSET));

            char[] buffer = new char[1024];
            int l = reader.read(buffer);
            StringBuilder scripts = new StringBuilder();
            while (l > 0) {
                scripts.append(buffer, 0, l);
                l = reader.read(buffer);
            }

            List<SQLStatement> statementList = SQLUtils.parseStatements(scripts.toString(), DbType.mysql);

            System.out.println(SQLUtils.toSQLString(statementList, DbType.h2));
        }

    }

    public static InputStream readFile(String name) throws IOException {
        InputStream input = new BufferedInputStream(new FileInputStream(name));
        PushbackInputStream pb = new PushbackInputStream( input, 2 );
        byte [] magicbytes = new byte[2];
        pb.read(magicbytes);
        pb.unread(magicbytes);
        ByteBuffer bb = ByteBuffer.wrap(magicbytes);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        short magic = bb.getShort();
        if( magic == (short)GZIPInputStream.GZIP_MAGIC )
            return new GZIPInputStream( pb );
        else
            return pb;
    }
}
