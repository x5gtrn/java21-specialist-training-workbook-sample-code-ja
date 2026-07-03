package com.x5gtrn.book.appendix.java21stw300.ch12_database_jdbc;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
@Component
public class Q169TransactionIsolationAnomalies implements FrameworkSample {
    private final DataSource dataSource;
    public Q169TransactionIsolationAnomalies(DataSource dataSource){ this.dataSource = dataSource; }
    public String chapter(){ return "12_Database_JDBC"; }
    public int problem(){ return 169; }
    public String title(){ return "Transaction Isolation と読み取り異常"; }
    public void run() throws Exception {
        try (Connection c = dataSource.getConnection()) {
            System.out.println("既定の分離レベル = " + name(c.getTransactionIsolation()));
            c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            System.out.println("設定後 = " + name(c.getTransactionIsolation()));
        }
        System.out.println("読み取り異常と、それを防ぐ最小の分離レベル:");
        System.out.println("  ダーティリード(未コミット読取) : READ_COMMITTED 以上で防止");
        System.out.println("  反復不能読取(再読で値が変化)   : REPEATABLE_READ 以上で防止");
        System.out.println("  ファントムリード(行の出現/消失) : SERIALIZABLE で防止");
        System.out.println("トレードオフ: 分離を上げるほど整合性は高いが、ロック競合で並行性/性能は下がる");
    }
    private String name(int level){
        return switch (level) {
            case Connection.TRANSACTION_READ_UNCOMMITTED -> "READ_UNCOMMITTED";
            case Connection.TRANSACTION_READ_COMMITTED -> "READ_COMMITTED";
            case Connection.TRANSACTION_REPEATABLE_READ -> "REPEATABLE_READ";
            case Connection.TRANSACTION_SERIALIZABLE -> "SERIALIZABLE";
            default -> "NONE(" + level + ")";
        };
    }
}
