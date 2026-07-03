// NG: コネクションリークの典型例
public User findUser(Long id) throws SQLException {
    Connection conn = dataSource.getConnection();  // POINT: 借り出し
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
    ps.setLong(1, id);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return mapToUser(rs);  // POINT: ここで例外発生すると...
    }
    conn.close();  // POINT: ここに到達しない → コネクションリーク！
    return null;
}

// OK: try-with-resources で確実に close
public User findUser(Long id) throws SQLException {
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
        ps.setLong(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() ? mapToUser(rs) : null;
        }
    }  // POINT: 例外発生有無に関わらず conn が確実に close → プールに返却
}

// ■ HikariCP のリーク検出設定
// 設定ファイル: application.properties
// spring.datasource.hikari.leak-detection-threshold=60000
// → 60秒以上返却されないコネクションに対してスタックトレース付き警告ログ出力
//
// POINT: ログ出力例:
// WARN  HikariPool - Connection leak detection triggered for conn0:url=...,
// スタックトレースが続く
// at com.app.UserDao.findUser(UserDao.java:25)  ← POINT: リーク箇所特定！
// スタック位置: com.app.UserService.getUser(UserService.java:42)