package com.x5gtrn.book.appendix.java21stw300.ch12_database_jdbc;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
@Component
public class Q161DataSourceVsDriverManager implements FrameworkSample {
    private final DataSource dataSource;
    public Q161DataSourceVsDriverManager(DataSource dataSource){ this.dataSource = dataSource; }
    public String chapter(){ return "12_Database_JDBC"; }
    public int problem(){ return 161; }
    public String title(){ return "DataSource vs DriverManager"; }
    public void run() throws Exception {
        // DataSource はコネクションプールから貸し出す。close() で物理切断せずプールへ返却される。
        try (Connection c1 = dataSource.getConnection()) {
            System.out.println("接続の実体クラス = " + c1.getClass().getSimpleName() + "（プールのプロキシ）");
            System.out.println("DB = " + c1.getMetaData().getDatabaseProductName());
        } // ここで物理切断ではなくプールへ返却
        try (Connection c2 = dataSource.getConnection()) {
            System.out.println("再取得も高速（プール再利用）。isValid=" + c2.isValid(1));
        }
        System.out.println("DriverManager.getConnection は毎回新しい物理接続を張るため実運用では非推奨");
    }
}
