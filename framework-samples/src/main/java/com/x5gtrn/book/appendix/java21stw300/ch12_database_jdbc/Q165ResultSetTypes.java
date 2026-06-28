package com.x5gtrn.book.appendix.java21stw300.ch12_database_jdbc;

import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 問題165: ResultSet の型（TYPE_SCROLL_INSENSITIVE）と並行性（CONCUR_READ_ONLY）。
 * スクロール可能な ResultSet では absolute / last / previous などでカーソルを自由に移動できる。
 */
@Component
public class Q165ResultSetTypes implements FrameworkSample {

    private final DataSource dataSource;

    public Q165ResultSetTypes(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String chapter() { return "12_Database_JDBC"; }
    public int problem()    { return 165; }
    public String title()   { return "ResultSet の型と並行性（スクロール可能カーソル）"; }

    public void run() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            try (Statement setup = conn.createStatement()) {
                setup.execute("CREATE TABLE IF NOT EXISTS demo_item(id INT PRIMARY KEY, name VARCHAR(50))");
                setup.execute("DELETE FROM demo_item");
                for (int i = 1; i <= 5; i++) {
                    setup.execute("INSERT INTO demo_item VALUES(" + i + ", 'item-" + i + "')");
                }
            }
            try (Statement scrollable = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                 ResultSet rs = scrollable.executeQuery("SELECT id, name FROM demo_item ORDER BY id")) {

                rs.last();
                System.out.println("last() -> 行数 = " + rs.getRow());
                rs.absolute(3);
                System.out.println("absolute(3) -> id=" + rs.getInt("id") + ", name=" + rs.getString("name"));
                rs.first();
                System.out.println("first() -> id=" + rs.getInt("id"));
                rs.afterLast();
                rs.previous();
                System.out.println("afterLast() then previous() -> id=" + rs.getInt("id"));
            }
        }
    }
}
