// D: 双方向スクロール + 更新可能
Statement stmt = conn.createStatement(
    ResultSet.TYPE_SCROLL_INSENSITIVE,  // POINT: 双方向スクロール
    ResultSet.CONCUR_UPDATABLE  // POINT: 更新可能
);
ResultSet rs = stmt.executeQuery("SELECT id, name, salary FROM employees");

// 逆方向スクロール
rs.last();  // 最後の行に移動
rs.previous();  // 前の行に移動
rs.absolute(5);  // 5番目の行に移動

// ResultSet を通じた行の更新
rs.absolute(3);
rs.updateDouble("salary", 95000.0);  // salary 列を更新
rs.updateRow();  // POINT: データベースに反映

// TYPE 定数の比較
// TYPE_FORWARD_ONLY:        前方のみ、スクロール不可
// TYPE_SCROLL_INSENSITIVE:  双方向、DB 変更を反映しない
// TYPE_SCROLL_SENSITIVE:    双方向、DB 変更を反映する

// CONCUR 定数の比較
// CONCUR_READ_ONLY:  読み取り専用
// CONCUR_UPDATABLE:  更新可能