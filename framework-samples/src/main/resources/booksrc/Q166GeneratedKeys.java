String sql = "insert into orders(customer_id, total_amount) values (?, ?)";

try (PreparedStatement ps = conn.prepareStatement(
        sql, Statement.RETURN_GENERATED_KEYS)) {
    ps.setLong(1, customerId);
    ps.setBigDecimal(2, total);
    int updated = ps.executeUpdate();
    if (updated != 1) {
        throw new SQLException("insert failed");
    }

    try (ResultSet keys = ps.getGeneratedKeys()) {
        if (!keys.next()) {
            throw new SQLException("generated key was not returned");
        }
        long orderId = keys.getLong(1);
        insertOrderItems(conn, orderId, items);
    }
}
@@BLOCK@@
long insertParent(Connection conn, Parent parent) throws SQLException {
    String sql = "insert into parent(name) values (?)";
    try (PreparedStatement ps = conn.prepareStatement(
            sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, parent.name());
        ps.executeUpdate();
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new SQLException("No generated key returned");
        }
    }
}
@@BLOCK@@
conn.setAutoCommit(false);
try {
    long parentId = insertParent(conn, parent);
    insertChildren(conn, parentId, children);
    conn.commit();
} catch (Exception ex) {
    conn.rollback();
    throw ex;
}