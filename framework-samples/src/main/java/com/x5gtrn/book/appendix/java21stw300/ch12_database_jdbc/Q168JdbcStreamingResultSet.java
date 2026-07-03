package com.x5gtrn.book.appendix.java21stw300.ch12_database_jdbc;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
@Component
public class Q168JdbcStreamingResultSet implements FrameworkSample {
    private final DataSource dataSource;
    public Q168JdbcStreamingResultSet(DataSource dataSource){ this.dataSource = dataSource; }
    public String chapter(){ return "12_Database_JDBC"; }
    public int problem(){ return 168; }
    public String title(){ return "JDBC ストリーミングと ResultSet"; }
    public void run() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            try (Statement st = conn.createStatement()) {
                st.execute("DROP TABLE IF EXISTS q168_events");
                st.execute("CREATE TABLE q168_events(id INT PRIMARY KEY, payload VARCHAR(20))");
                for (int i = 1; i <= 1000; i++) st.execute("INSERT INTO q168_events VALUES(" + i + ", 'p" + i + "')");
            }
            // 大量行を一度に読み込まず、フェッチサイズを絞って逐次処理する（メモリ節約）。
            // PostgreSQL では autoCommit=false + forward-only + fetchSize がストリーミングの条件。
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT id FROM q168_events ORDER BY id",
                    ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
                ps.setFetchSize(50);
                long sum = 0; int rows = 0;
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) { sum += rs.getInt(1); rows++; }
                }
                System.out.println("fetchSize=50 で " + rows + " 行を逐次処理、id 合計=" + sum);
            }
            try (Statement st = conn.createStatement()) { st.execute("DROP TABLE IF EXISTS q168_events"); }
        }
    }
}
