package com.x5gtrn.book.appendix.java21stw300.ch12_database_jdbc;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
@Component
public class Q170QueryTimeout implements FrameworkSample {
    private final DataSource dataSource;
    public Q170QueryTimeout(DataSource dataSource){ this.dataSource = dataSource; }
    public String chapter(){ return "12_Database_JDBC"; }
    public int problem(){ return 170; }
    public String title(){ return "クエリ Timeout とキャンセル設計"; }
    public void run() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            try (Statement st = conn.createStatement()) {
                st.execute("DROP TABLE IF EXISTS q170_data");
                st.execute("CREATE TABLE q170_data(id INT PRIMARY KEY)");
                for (int i = 1; i <= 100; i++) st.execute("INSERT INTO q170_data VALUES(" + i + ")");
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM q170_data")) {
                ps.setQueryTimeout(10); // 10 秒を超えたら JDBC ドライバがキャンセルし SQLException
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    System.out.println("count=" + rs.getInt(1) + "（queryTimeout=10s 以内に完了）");
                }
            }
            System.out.println("長時間クエリは setQueryTimeout でキャンセルし、スレッド枯渇を防ぐ");
            try (Statement st = conn.createStatement()) { st.execute("DROP TABLE IF EXISTS q170_data"); }
        }
    }
}
