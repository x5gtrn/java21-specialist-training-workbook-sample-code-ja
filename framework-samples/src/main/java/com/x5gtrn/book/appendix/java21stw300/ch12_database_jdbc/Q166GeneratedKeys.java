package com.x5gtrn.book.appendix.java21stw300.ch12_database_jdbc;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
@Component
public class Q166GeneratedKeys implements FrameworkSample {
    private final DataSource dataSource;
    public Q166GeneratedKeys(DataSource dataSource){ this.dataSource = dataSource; }
    public String chapter(){ return "12_Database_JDBC"; }
    public int problem(){ return 166; }
    public String title(){ return "Generated Keys の取得"; }
    public void run() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            try (Statement st = conn.createStatement()) {
                st.execute("DROP TABLE IF EXISTS q166_orders");
                st.execute("CREATE TABLE q166_orders(id BIGINT AUTO_INCREMENT PRIMARY KEY, customer VARCHAR(50))");
            }
            // RETURN_GENERATED_KEYS を指定して、DB が採番した自動生成キーを取得する
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO q166_orders(customer) VALUES(?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, "Alice");
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) System.out.println("生成された主キー id = " + keys.getLong(1));
                }
            }
            try (Statement st = conn.createStatement()) { st.execute("DROP TABLE IF EXISTS q166_orders"); }
        }
    }
}
