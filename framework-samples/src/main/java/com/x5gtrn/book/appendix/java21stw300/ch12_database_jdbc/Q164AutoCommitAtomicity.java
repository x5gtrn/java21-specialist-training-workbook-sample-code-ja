package com.x5gtrn.book.appendix.java21stw300.ch12_database_jdbc;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
@Component
public class Q164AutoCommitAtomicity implements FrameworkSample {
    private final DataSource dataSource;
    public Q164AutoCommitAtomicity(DataSource dataSource){ this.dataSource = dataSource; }
    public String chapter(){ return "12_Database_JDBC"; }
    public int problem(){ return 164; }
    public String title(){ return "auto-commit と複数 SQL の原子性"; }
    public void run() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            try (Statement st = conn.createStatement()) {
                st.execute("DROP TABLE IF EXISTS q164_orders");
                st.execute("CREATE TABLE q164_orders(id INT PRIMARY KEY, item VARCHAR(30))");
            }
            // auto-commit=true では 1 文ごとに即コミット。2 文目が失敗しても 1 文目は残り原子性が崩れる。
            try (Statement st = conn.createStatement()) {
                st.executeUpdate("INSERT INTO q164_orders VALUES(1, 'A')"); // 即コミット
                try { st.executeUpdate("INSERT INTO q164_orders VALUES(1, 'B')"); } // 主キー重複で失敗
                catch (Exception e) { System.out.println("2件目は失敗: " + e.getClass().getSimpleName()); }
            }
            try (Statement st = conn.createStatement();
                 var rs = st.executeQuery("SELECT COUNT(*) FROM q164_orders")) {
                rs.next();
                System.out.println("残った行数 = " + rs.getInt(1) + "（1件目はコミット済み。複数SQLはトランザクションで束ねるべき）");
            }
            try (Statement st = conn.createStatement()) { st.execute("DROP TABLE IF EXISTS q164_orders"); }
        }
    }
}
