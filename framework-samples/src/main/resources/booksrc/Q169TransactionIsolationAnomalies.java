try (Connection connection = dataSource.getConnection()) {
    connection.setAutoCommit(false);
    connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

    // 読み取り、検証、更新

    connection.commit();
} catch (SQLException ex) {
    // ロールバック処理
}
@@BLOCK@@
try (Connection conn = dataSource.getConnection()) {
    conn.setAutoCommit(false);
    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
    try {
        BigDecimal balance = readBalance(conn, accountId);
        validate(balance);
        updateBalance(conn, accountId);
        conn.commit();
    } catch (SQLException | RuntimeException ex) {
        conn.rollback();
        throw ex;
    }
}