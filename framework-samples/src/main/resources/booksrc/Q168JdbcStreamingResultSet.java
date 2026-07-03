// ■ PostgreSQL でのストリーミング
Connection conn = dataSource.getConnection();
conn.setAutoCommit(false);  // POINT: PostgreSQL: autoCommit=false が必須条件

Statement stmt = conn.createStatement(
    ResultSet.TYPE_FORWARD_ONLY,  // POINT: 前方のみ（後方スクロール不要）
    ResultSet.CONCUR_READ_ONLY  // POINT: 読み取り専用
);
stmt.setFetchSize(100);  // POINT: 100行ずつフェッチ

ResultSet rs = stmt.executeQuery("SELECT * FROM large_table");
try (CSVWriter writer = new CSVWriter(new FileWriter("export.csv"))) {
    while (rs.next()) {
        writer.writeNext(new String[]{
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("email")
        });
        // POINT: 行ごとに処理 → メモリ使用量は fetchSize × 行サイズ で一定
    }
}

// ■ MySQL でのストリーミング（特殊な設定）
Statement stmt = conn.createStatement(
    ResultSet.TYPE_FORWARD_ONLY,
    ResultSet.CONCUR_READ_ONLY
);
stmt.setFetchSize(Integer.MIN_VALUE);  // POINT: MySQL 固有: ストリーミングモード有効化
// MySQL Connector/J では Integer.MIN_VALUE が「1行ずつストリーミング」のシグナル

// ■ Spring Data JPA でのストリーミング
@QueryHints(@QueryHint(name = HINT_FETCH_SIZE, value = "100"))
@Query("SELECT u FROM User u")
Stream<User> streamAll();
// → try-with-resources で Stream を使用すること