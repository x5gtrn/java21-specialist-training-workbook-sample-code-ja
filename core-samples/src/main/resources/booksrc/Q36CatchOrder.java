// OK: 正しい順序（具体的 → 一般的）
try {
    processFile(path);
} catch (FileNotFoundException e) {  // POINT: IOException のサブクラス（最も具体的）
    log.warn("File not found: {}", path);
    useDefaultConfig();
} catch (IOException e) {  // POINT: Exception のサブクラス
    log.error("I/O error: {}", e.getMessage());
    throw new ServiceException("File processing failed", e);
} catch (Exception e) {  // POINT: 最も一般的（フォールバック）
    log.error("Unexpected error", e);
    throw new ServiceException("Unexpected failure", e);
}

// NG: コンパイルエラー（一般的 → 具体的）
try { processFile(path); }
catch (IOException e) { }  // POINT: FileNotFoundException もここでキャッチされる
catch (FileNotFoundException e) { }  // NG: 到達不能コード → コンパイルエラー
// エラー: exception FileNotFoundException has already been caught

// ■ Java 7+ マルチキャッチ（兄弟関係の例外を1つの catch で処理）
try { processData(input); }
catch (IOException | ParseException e) {  // POINT: 兄弟関係（親子ではない）
    log.error("Processing failed: {}", e.getMessage());
}
// → IOException と ParseException は親子関係ではないので同じ catch に書ける
// → 親子関係（IOException | FileNotFoundException）はコンパイルエラー

// ■ 例外階層
// Throwable
// ├─ Error （JVM 致命的エラー: OutOfMemoryError 等）
// └─ Exception
//      ├─ RuntimeException （非チェック例外）
//      │    ├─ NullPointerException
//      │    ├─ IllegalArgumentException
//      │    │    └─ NumberFormatException
//      │    └─ ClassCastException
//      └─ IOException （チェック例外）
//           └─ FileNotFoundException