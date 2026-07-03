BufferedReader reader = new BufferedReader(new FileReader("data.csv"));
try (reader) {
    String line = reader.readLine();
    System.out.println(line);
}
@@BLOCK@@
// Java 8 以前: try 括弧内での宣言が必須
try (BufferedReader reader = new BufferedReader(new FileReader("data.csv"))) {
    // ...
}

// Java 9 以降: effectively final な事前宣言変数が使用可能
BufferedReader reader = new BufferedReader(new FileReader("data.csv"));
try (reader) {  // POINT: effectively final なので直接参照可能
    String line = reader.readLine();
}
// ↑ ここで reader.close() が自動的に呼ばれる

// NG: 再代入すると effectively final でなくなり、コンパイルエラー
BufferedReader reader2 = new BufferedReader(new FileReader("a.csv"));
reader2 = new BufferedReader(new FileReader("b.csv"));  // 再代入
try (reader2) {  // ← コンパイルエラー: effectively final ではない
    // ...
}