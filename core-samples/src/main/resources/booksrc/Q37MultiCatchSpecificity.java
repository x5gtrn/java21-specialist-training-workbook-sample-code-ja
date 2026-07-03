try {
    parseData(input);
} catch (FileNotFoundException | IOException e) {
    logger.error("Import failed", e);
}
@@BLOCK@@
// NG: コンパイルエラー: FileNotFoundException は IOException のサブクラス
try {
    parseData(input);
} catch (FileNotFoundException | IOException e) {  // エラー
    logger.error("Import failed", e);
}

// OK: 修正方法1: 親クラスのみキャッチ
try {
    parseData(input);
} catch (IOException e) {
    logger.error("Import failed", e);
}

// OK: 修正方法2: 異なるハンドリングが必要な場合は個別の catch ブロック
try {
    parseData(input);
} catch (FileNotFoundException e) {
    logger.error("File not found: " + e.getMessage());
} catch (IOException e) {
    logger.error("I/O error", e);
}

// OK: 有効なマルチキャッチ: 互いに無関係な例外型
try {
    parseData(input);
} catch (IOException | ParseException e) {  // OK: 階層上無関係
    logger.error("Import failed", e);
}