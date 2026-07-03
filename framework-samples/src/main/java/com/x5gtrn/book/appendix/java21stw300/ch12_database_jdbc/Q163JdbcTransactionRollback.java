package com.x5gtrn.book.appendix.java21stw300.ch12_database_jdbc;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
@Component
public class Q163JdbcTransactionRollback implements FrameworkSample {
    private final DataSource dataSource;
    public Q163JdbcTransactionRollback(DataSource dataSource){ this.dataSource = dataSource; }
    public String chapter(){ return "12_Database_JDBC"; }
    public int problem(){ return 163; }
    public String title(){ return "JDBC トランザクションのロールバック"; }
    public void run() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            try (Statement st = conn.createStatement()) {
                st.execute("DROP TABLE IF EXISTS q163_account");
                st.execute("CREATE TABLE q163_account(id INT PRIMARY KEY, balance INT)");
                st.execute("INSERT INTO q163_account VALUES(1, 1000),(2, 0)");
            }
            conn.setAutoCommit(false); // 明示的トランザクション開始
            try (Statement st = conn.createStatement()) {
                st.executeUpdate("UPDATE q163_account SET balance = balance - 500 WHERE id = 1");
                if (true) throw new IllegalStateException("送金処理中に業務エラー発生");
            } catch (Exception e) {
                conn.rollback(); // 途中の更新をすべて取り消す
                System.out.println("ロールバック実行: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }
            try (Statement st = conn.createStatement();
                 var rs = st.executeQuery("SELECT balance FROM q163_account WHERE id = 1")) {
                rs.next();
                System.out.println("id=1 の残高 = " + rs.getInt(1) + "（1000 のまま。更新は取り消された）");
            }
            try (Statement st = conn.createStatement()) { st.execute("DROP TABLE IF EXISTS q163_account"); }
        }
    }
}
