package com.x5gtrn.book.appendix.java21stw300.ch12_database_jdbc;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
@Component
public class Q162ConnectionPooling implements FrameworkSample {
    private final DataSource dataSource;
    public Q162ConnectionPooling(DataSource dataSource){ this.dataSource = dataSource; }
    public String chapter(){ return "12_Database_JDBC"; }
    public int problem(){ return 162; }
    public String title(){ return "コネクションプーリングと HikariCP"; }
    public void run() throws Exception {
        // Spring Boot の既定プールは HikariCP。接続の生成/破棄を都度行わず、貸出/返却で再利用する。
        System.out.println("DataSource 実装 = " + dataSource.getClass().getName());
        for (int i = 1; i <= 3; i++) {
            try (Connection c = dataSource.getConnection()) {   // プールから借用
                System.out.println("  借用" + i + ": valid=" + c.isValid(1) + " (close でプールへ返却)");
            } // close は物理切断でなくプールへの返却
        }
        System.out.println("接続生成は高コスト → プールで再利用し、maximumPoolSize で同時接続数を制御する");
        System.out.println("要点: プールサイズは『コア数×2＋実効スピンドル』程度が目安。leakDetectionThreshold で漏れ検知");
    }
}
