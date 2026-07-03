// NG: 文字列連結（SQL インジェクション脆弱）
String userInput = "'; DROP TABLE products; --";
String sql = "SELECT * FROM products WHERE name = '" + userInput + "'";
// 結果の SQL:
// SELECT * FROM products WHERE name = ''; DROP TABLE products; --'
// → products テーブルが削除される！

// OK: PreparedStatement（安全）
PreparedStatement ps = conn.prepareStatement(
    "SELECT * FROM products WHERE name = ?");
ps.setString(1, userInput);  // POINT: パラメータバインディング
// データベースに送信されるもの:
// SQL 構造: "SELECT * FROM products WHERE name = ?"
// パラメータ: "'; DROP TABLE products; --"（データとして扱われる）
// → 名前が「'; DROP TABLE products; --」という商品を検索するだけ
// → テーブルは削除されない OK:

// ■ Spring Data JPA でも安全
@Query("SELECT p FROM Product p WHERE p.name = :name")
List<Product> findByName(@Param("name") String name);
// → :name はパラメータバインディング → SQL インジェクション不可

// ■ Spring Data JPA の派生クエリも安全
List<Product> findByNameContaining(String keyword);
// → 内部的に PreparedStatement + パラメータバインディング

// ■ 危険なパターン: 動的 SQL 構築（JPA でも発生し得る）
// NG: String jpql = "SELECT p FROM Product p WHERE p.name = '" + input + "'";
// NG: entityManager.createQuery(jpql);  → SQL インジェクション脆弱！