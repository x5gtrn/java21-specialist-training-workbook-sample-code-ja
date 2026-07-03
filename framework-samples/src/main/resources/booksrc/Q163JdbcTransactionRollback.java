public void processOrder(String orderId, int productId, int quantity) throws ServiceException {
    Connection conn = null;
    try {
        conn = dataSource.getConnection();
        conn.setAutoCommit(false);  // POINT: トランザクション開始

        // 操作1: 注文挿入
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO orders (id, product_id, quantity) VALUES (?, ?, ?)")) {
            ps.setString(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        }

        // 操作2: 在庫更新
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE inventory SET stock = stock - ? WHERE product_id = ? AND stock >= ?")) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new SQLException("Insufficient stock for product: " + productId);
            }
        }

        conn.commit();  // POINT: 両方成功 → コミット

    } catch (SQLException e) {
        if (conn != null) {
            try { conn.rollback(); }  // POINT: いずれか失敗 → 全ロールバック
            catch (SQLException re) { log.error("Rollback failed", re); }
        }
        throw new ServiceException("Order processing failed", e);
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);  // auto-commit を復元
                conn.close();  // 接続をプールに返却
            } catch (SQLException e) { log.error("Connection cleanup failed", e); }
        }
    }
}

// POINT: Spring Boot では @Transactional で宣言的に管理（JDBC 直接管理は不要）
@Transactional
public void processOrder(OrderRequest req) {
    orderRepository.save(new Order(req));
    inventoryRepository.decrementStock(req.productId(), req.quantity());
    // → 例外発生時は Spring が自動 rollback
}