package com.liu.test.calcite;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.calcite.adapter.jdbc.JdbcSchema;
import org.apache.calcite.config.Lex;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.dialect.MysqlSqlDialect;
import org.apache.calcite.sql.dialect.OracleSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class MysqlTets {
    public static void main(String[] args) throws Exception {
        parse();
    }

    public static void parse() throws SqlParseException {
        // Sql语句
        String sql = "select * from laiye_bss_hrm.crm_sales_order where id = 1";
        // 解析配置
        SqlParser.Config mysqlConfig = SqlParser.configBuilder().setLex(Lex.MYSQL).build();
        // 创建解析器
        SqlParser parser = SqlParser.create(sql, mysqlConfig);
        // 解析sql
        SqlNode sqlNode = parser.parseQuery();
        sqlNode.accept(null);
        // 还原某个方言的SQL
        System.out.println(sqlNode.toSqlString(MysqlSqlDialect.DEFAULT));
    }
    public static void conn() throws Exception{
        Class.forName("org.apache.calcite.jdbc.Driver");
        Class.forName("com.mysql.cj.jdbc.Driver");

        // the properties for calcite connection
        Properties info = new Properties();
        info.setProperty("lex", "JAVA");
        info.setProperty("remarks","true");
        // SqlParserImpl can analysis sql dialect for sql parse
        info.setProperty("parserFactory","org.apache.calcite.sql.parser.impl.SqlParserImpl#FACTORY");

        // create calcite connection and schema
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        System.out.println(calciteConnection.getProperties());
        SchemaPlus rootSchema = calciteConnection.getRootSchema();

        // code for mysql datasource
        MysqlDataSource dataSource = new MysqlDataSource();
        // please change host and port maybe like "jdbc:mysql://127.0.0.1:3306/test"
        dataSource.setUrl("jdbc:mysql://am-2ze35342yd571e4i490650.ads.aliyuncs.com:3306/laiye_bss_hrm");
        dataSource.setUser("im_adb_root");
        dataSource.setPassword("CAVWCIQO1zgwSXgAguHGR9KLpaqehCbH");
        // mysql schema, the sub schema for rootSchema, "test" is a schema in mysql
        Schema schema = JdbcSchema.create(rootSchema, "laiye_bss_hrm", dataSource, null, "laiye_bss_hrm");
        rootSchema.add("laiye_bss_hrm", schema);

        // run sql query
        Statement statement = calciteConnection.createStatement();
//        ResultSet resultSet = statement.executeQuery("select * from laiye_bss_hrm.crm_sales_order o1 left join laiye_bss_hrm.crm_gathering o2 on o1.id = o2.id");
        ResultSet resultSet = statement.executeQuery("select * from laiye_bss_hrm.crm_sales_order o1 left join laiye_bss_hrm.crm_sales_order o2 on o1.id = o2.id ");
        while (resultSet.next()) {
            System.out.println(resultSet.getObject(1) + "__" + resultSet.getObject(2));
        }

        statement.close();
        connection.close();
    }
}
