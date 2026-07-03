// DriverManager — 各呼び出しで新しい物理接続を作成
Connection conn = DriverManager.getConnection(
    "jdbc:postgresql://localhost:5432/mydb", "user", "pass");
// → TCP 接続確立 → 認証 → 初期化（数十〜数百ミリ秒）

// DataSource（例: HikariCP）— プールから接続を取得
HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:postgresql://localhost:5432/mydb");
config.setUsername("user");
config.setPassword("pass");
config.setMaximumPoolSize(10);

DataSource dataSource = new HikariDataSource(config);
Connection conn = dataSource.getConnection();
// → プールから既存接続を取得（マイクロ秒〜数ミリ秒）

// Spring Boot での DataSource 自動設定
// 設定ファイル: application.properties
// spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
// spring.datasource.hikari.maximum-pool-size=10