@Service
class OrderService {
    public void process() {
        // ...
        saveAudit();  // 自己呼び出し
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAudit() {
        // 期待される新規トランザクション
    }
}
@@BLOCK@@
@Service
class AuditService {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAudit(AuditEvent event) {
        // 新規トランザクション
    }
}
@@BLOCK@@
@Service
class OrderService {
    private final AuditService auditService;

    OrderService(AuditService auditService) {
        this.auditService = auditService;
    }

    public void process() {
        auditService.saveAudit(new AuditEvent("ORDER_PROCESSED"));
    }
}