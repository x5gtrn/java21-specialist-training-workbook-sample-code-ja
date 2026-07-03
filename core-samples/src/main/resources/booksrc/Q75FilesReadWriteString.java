// 読み取り
String content = Files.readString(Path.of("config.json"));  // UTF-8（デフォルト）
String sjis = Files.readString(Path.of("data.txt"), Charset.forName("Shift_JIS"));  // 文字セット指定

// 書き込み
Files.writeString(Path.of("output.txt"), "Hello, Java 21!");  // デフォルト: 新規作成 or 上書き
Files.writeString(Path.of("log.txt"), "new entry\n",
    StandardOpenOption.APPEND, StandardOpenOption.CREATE);  // 追記モード

// 比較: Java 11 以前のファイル読み取り
// Java 8 方式（冗長）
String old = new String(Files.readAllBytes(Path.of("config.json")), StandardCharsets.UTF_8);

// Java 11+ 方式（簡潔）
String modern = Files.readString(Path.of("config.json"));