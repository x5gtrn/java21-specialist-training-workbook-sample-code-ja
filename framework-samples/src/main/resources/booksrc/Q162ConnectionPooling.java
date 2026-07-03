// POINT: 接続リークの典型例（BAD）
public User findUser(String id) {
    Connection conn = dataSource.getConnection();
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
    ps.setString(1, id);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return new User(rs.getString("name"));
    }
    // NG: conn.close() が呼ばれない → 接続がプールに返却されない！
    return null;
}

// POINT: 正しい接続管理（GOOD）
public User findUser(String id) {
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
        ps.setString(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new User(rs.getString("name"));
            }
        }
    }  // ← try-with-resources で確実に close → プールに返却
    return null;
}

// HikariCP の接続リーク検出設定
// spring.datasource.hikari.leak-detection-threshold=60000  # 60秒