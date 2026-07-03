try (PreparedStatement ps = conn.prepareStatement(sql)) {
    ps.setQueryTimeout(10);  // 秒
    try (ResultSet rs = ps.executeQuery()) {
        // 結果を読み取る
    }
}
@@BLOCK@@
@Transactional(timeout = 10)
public Report loadReport(ReportCommand command) {
    return repository.load(command);
}
@@BLOCK@@
String sql = "select * from report_rows where report_id = ?";
try (Connection conn = dataSource.getConnection();
     PreparedStatement ps = conn.prepareStatement(sql)) {
    ps.setLong(1, reportId);
    ps.setQueryTimeout(15);
    try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            consume(rs);
        }
    }
}