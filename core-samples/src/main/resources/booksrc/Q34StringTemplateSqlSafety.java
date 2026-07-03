String user = request.getParameter("user");
String sql = STR."select * from users where name = '\{user}'";
@@BLOCK@@
String sql = "select * from users where name = ?";
try (PreparedStatement ps = connection.prepareStatement(sql)) {
    ps.setString(1, user);
}
@@BLOCK@@
// 説明用: SQL として安全とは限らない
String unsafeSql = STR."select * from users where email = '\{email}'";

// 基本形: 値を bind する
String safeSql = "select * from users where email = ?";
try (PreparedStatement ps = connection.prepareStatement(safeSql)) {
    ps.setString(1, email);
}