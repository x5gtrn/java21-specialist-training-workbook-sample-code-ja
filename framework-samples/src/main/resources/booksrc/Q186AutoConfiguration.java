// ■ 自動構成の内部動作（概念的な疑似コード）
@AutoConfiguration
@ConditionalOnClass(DataSource.class)  // JDBC がクラスパスにある場合
@ConditionalOnMissingBean(DataSource.class)  // 開発者が DataSource を未定義の場合
public class DataSourceAutoConfiguration {

    @Bean
    @ConditionalOnClass(name = "org.h2.Driver")  // H2 がクラスパスにある場合
    public DataSource h2DataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build();
    }
}

// ■ 開発者がオーバーライドする場合
@Configuration
public class MyDataSourceConfig {
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://prod-db:5432/myapp");
        config.setUsername("admin");
        return new HikariDataSource(config);
        // POINT: この定義が自動構成の DataSource より優先される
    }
}

// ■ 自動構成の判断チェーン
// 1. spring-boot-starter-data-jpa → JPA 関連クラスがクラスパスに
// 2. H2 の依存 → org.h2.Driver がクラスパスに
// 3. @ConditionalOnClass(DataSource.class) → true
// 4. @ConditionalOnMissingBean(DataSource.class) → true（開発者未定義）
// 5. → インメモリ H2 DataSource Bean を自動作成
// 6. JPA 関連: EntityManagerFactory, TransactionManager も連鎖的に自動構成

// ■ 自動構成の確認方法
// --debug フラグで自動構成レポートを出力
// java -jar app.jar --debug
// → Positive matches（適用された自動構成）
// → Negative matches（条件不一致で適用されなかった自動構成）