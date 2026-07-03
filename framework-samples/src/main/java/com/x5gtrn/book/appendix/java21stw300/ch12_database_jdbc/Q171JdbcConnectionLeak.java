package com.x5gtrn.book.appendix.java21stw300.ch12_database_jdbc;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
@Component
public class Q171JdbcConnectionLeak implements FrameworkSample {
    private final DataSource dataSource;
    public Q171JdbcConnectionLeak(DataSource dataSource){ this.dataSource = dataSource; }
    public String chapter(){ return "12_Database_JDBC"; }
    public int problem(){ return 171; }
    public String title(){ return "JDBC コネクションリーク検出"; }
    public void run() throws Exception {
        // try-with-resources で借りた接続を必ず返却する。例外時も close されるのが要点。
        for (int i = 0; i < 5; i++) {
            try (Connection conn = dataSource.getConnection();
                 Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT 1")) {
                rs.next();
            } // ここで確実にプールへ返却
        }
        System.out.println("5 回の借用をすべて try-with-resources で返却（リークなし）");
        System.out.println("NG 例: close 忘れやエラー経路での未返却はプール枯渇を招き、");
        System.out.println("HikariCP の leakDetectionThreshold でリーク警告を検出できる");
    }
}
