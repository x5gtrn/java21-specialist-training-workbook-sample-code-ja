@Scheduled(fixedDelay = 60_000)
void processInvoices() {
    List<Invoice> invoices = repository.findPending();
    invoices.forEach(this::process);
}
@@BLOCK@@
@Transactional
public boolean claimInvoice(long invoiceId, String workerId) {
    int updated = jdbcTemplate.update("""
        update invoices
        set status = 'PROCESSING', worker_id = ?
        where id = ? and status = 'PENDING'
        """, workerId, invoiceId);
    return updated == 1;
}