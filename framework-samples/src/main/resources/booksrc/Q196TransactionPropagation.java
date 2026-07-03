@Service
public class OrderService {
    private final AuditService auditService;
    private final OrderRepository orderRepository;

    @Transactional  // デフォルト: Propagation.REQUIRED
    public void processOrder(Order order) {
        orderRepository.save(order);  // ← トランザクション A に含まれる

        auditService.saveAuditLog(order);  // ← トランザクション B（独立）で実行
        // ↑ この時点でトランザクション B はコミット済み

        validateInventory(order);  // ← ここで例外発生！
        // → トランザクション A がロールバック
        // → しかしトランザクション B は既にコミット済みなので影響なし
    }
}

@Service
public class AuditService {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAuditLog(Order order) {
        // POINT: 新しい独立トランザクション（トランザクション B）で実行
        auditRepository.save(new AuditLog("ORDER_CREATED", order.getId()));
        // メソッド正常終了 → トランザクション B コミット
    }
}

// POINT: 重要な注意点: 同一クラス内のメソッド呼び出し
// self.saveAuditLog(order) — 同一クラス内の @Transactional メソッド呼び出しは
// Spring プロキシを経由しないため、REQUIRES_NEW が適用されない！
// → 別の Bean に分離する必要がある

// ■ 主要な伝播レベルの比較
// REQUIRED (デフォルト) → 既存トランザクションに参加、なければ新規作成
// REQUIRES_NEW          → 常に新規トランザクション（既存は一時停止）
// MANDATORY             → 既存トランザクション必須（なければ例外スロー）
// SUPPORTS              → あれば参加、なければ非トランザクションで実行
// NOT_SUPPORTED         → トランザクション外で実行（既存は一時停止）
// NESTED                → 既存内にセーブポイント付きネストトランザクション
// NEVER                 → トランザクション禁止（あれば例外スロー）