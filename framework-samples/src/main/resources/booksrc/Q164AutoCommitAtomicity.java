try (Connection conn = dataSource.getConnection()) {
    insertOrder(conn, order);  // auto-commit ならここで commit
    decrementInventory(conn, item);  // ここで失敗しても order は残る
}
@@BLOCK@@
try (Connection conn = dataSource.getConnection()) {
    conn.setAutoCommit(false);
    try {
        insertOrder(conn, order);
        decrementInventory(conn, item);
        conn.commit();
    } catch (SQLException | RuntimeException ex) {
        conn.rollback();
        throw ex;
    }
}
@@BLOCK@@
try (Connection conn = dataSource.getConnection()) {
    boolean oldAutoCommit = conn.getAutoCommit();
    conn.setAutoCommit(false);
    try {
        insertOrder(conn, order);
        decrementInventory(conn, order.itemId(), order.quantity());
        conn.commit();
    } catch (Exception ex) {
        conn.rollback();
        throw ex;
    } finally {
        conn.setAutoCommit(oldAutoCommit);
    }
}
@@BLOCK@@
@Transactional
public void placeOrder(OrderCommand command) {
    orderRepository.insert(command);
    inventoryRepository.decrement(command.itemId(), command.quantity());
}