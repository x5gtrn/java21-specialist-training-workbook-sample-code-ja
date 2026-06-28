// intern() — 手動重複排除
String s1 = new String("hello").intern();
String s2 = "hello";
System.out.println(s1 == s2);  // true（プール内の同一参照）

// Compact Strings — 自動メモリ最適化
"Hello"  // Latin-1 → byte[5]  (1バイト/文字)
"こんにちは"  // UTF-16 → byte[10] (2バイト/文字)

// String Deduplication — JVM フラグのみ
// java -XX:+UseStringDeduplication -XX:+UseG1GC -jar app.jar