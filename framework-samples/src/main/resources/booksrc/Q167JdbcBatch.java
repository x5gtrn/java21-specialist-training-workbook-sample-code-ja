Connection conn = dataSource.getConnection();
conn.setAutoCommit(false);  // POINT: auto-commit を無効化（推奨）

String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
PreparedStatement pstmt = conn.prepareStatement(sql);

int batchSize = 1000;  // バッチサイズ
for (int i = 0; i < 100_000; i++) {
    pstmt.setString(1, "User " + i);
    pstmt.setString(2, "user" + i + "@example.com");
    pstmt.addBatch();  // POINT: バッチに追加（まだ実行されない）

    if ((i + 1) % batchSize == 0) {
        int[] counts = pstmt.executeBatch();  // POINT: まとめて実行
        // counts[0] = 1, counts[1] = 1, ... （各文の更新行数）
    }
}
int[] remaining = pstmt.executeBatch();  // 残りを実行
conn.commit();  // POINT: 全体をコミット