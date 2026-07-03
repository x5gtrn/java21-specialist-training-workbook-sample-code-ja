// 内部表現（概念的な構造）
// Java 8 以前:
// class String { private final char[] value; }  // 常に2バイト/文字

// Java 9 以降（Compact Strings）:
// class String {
// private final byte[] value;   // 内部バイト配列
// private final byte coder;     // LATIN1=0, UTF16=1
// }

String ascii = "Hello";  // coder=LATIN1, 5バイト（1バイト/文字）
String jp = "こんにちは";  // coder=UTF16, 10バイト（2バイト/文字）
String mixed = "Hello世界";  // coder=UTF16, 14バイト（2バイト/文字、全体が UTF16）

// メモリ比較（"Hello"の場合）
// Java 8: char[5] = 10バイト
// Java 9+: byte[5] = 5バイト → 50%削減