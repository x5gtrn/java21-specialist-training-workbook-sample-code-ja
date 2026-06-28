try (var connection = dataSource.getConnection()) {
    processRecords(connection);  // DataProcessingException をスロー
}  // connection.close() が ConnectionCloseException をスロー
@@BLOCK@@
try {
    try (var connection = dataSource.getConnection()) {
        processRecords(connection);
    }
} catch (Exception e) {
    // e は DataProcessingException（主例外）
    System.out.println("Primary: " + e.getClass().getSimpleName());

    // 抑制された例外を取得
    Throwable[] suppressed = e.getSuppressed();
    for (Throwable s : suppressed) {
        // s は ConnectionCloseException（抑制された例外）
        System.out.println("Suppressed: " + s.getClass().getSimpleName());
    }
}

// 出力:
// Primary: DataProcessingException
// Suppressed: ConnectionCloseException