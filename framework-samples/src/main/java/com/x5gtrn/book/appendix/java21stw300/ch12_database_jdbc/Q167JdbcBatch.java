package com.x5gtrn.book.appendix.java21stw300.ch12_database_jdbc;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
@Component
public class Q167JdbcBatch implements FrameworkSample {
    private final DataSource dataSource;
    public Q167JdbcBatch(DataSource dataSource){ this.dataSource = dataSource; }
    public String chapter(){ return "12_Database_JDBC"; }
    public int problem(){ return 167; }
    public String title(){ return "JDBC バッチ処理"; }
    public void run() throws Exception {
        try (Connection c = dataSource.getConnection()) {
            try (Statement st = c.createStatement()) {
                st.execute("CREATE TABLE IF NOT EXISTS q167_batch(id INT PRIMARY KEY, name VARCHAR(50))");
                st.execute("DELETE FROM q167_batch");
            }
            c.setAutoCommit(false);
            long start = System.currentTimeMillis();
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO q167_batch(id, name) VALUES (?, ?)")) {
                for (int i = 1; i <= 1000; i++) {
                    ps.setInt(1, i); ps.setString(2, "name-" + i);
                    ps.addBatch();                        // まとめて送るためにバッチへ追加
                    if (i % 200 == 0) ps.executeBatch();  // 適度な塊で実行（メモリ抑制）
                }
                ps.executeBatch();
            }
            c.commit();
            long ms = System.currentTimeMillis() - start;
            int count;
            try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery("SELECT count(*) FROM q167_batch")) {
                rs.next(); count = rs.getInt(1);
            }
            try (Statement st = c.createStatement()) { st.execute("DROP TABLE q167_batch"); }
            System.out.println("バッチで " + count + " 件を挿入（" + ms + " ms）");
            System.out.println("1件ずつの往復に対し、addBatch/executeBatch はDB往復を大幅削減し高速化する");
            System.out.println("補足: rewriteBatchedStatements(MySQL)等のドライバ設定で更に効く。巨大バッチは分割する");
        }
    }
}
