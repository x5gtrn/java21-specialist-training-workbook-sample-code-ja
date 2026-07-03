// NG: メモリリークの例
@Service  // POINT: シングルトン → アプリケーション全体の生存期間
public class SessionCache {
    private final Map<String, UserSession> sessions = new HashMap<>();

    public void addSession(String userId, UserSession session) {
        sessions.put(userId, session);  // POINT: 追加のみ、削除なし
    }

    public Optional<UserSession> getSession(String userId) {
        return Optional.ofNullable(sessions.get(userId));
    }
    // POINT: removeSession() メソッドがない → エントリは永久に蓄積
    // → 1日1000ユーザー × 30日 = 30,000エントリが蓄積
    // → UserSession がさらに大きなオブジェクトを参照 → メモリ枯渇
}

// OK: 修正策1: TTL 付きキャッシュライブラリ（推奨）
@Service
public class SessionCache {
    private final Cache<String, UserSession> sessions = Caffeine.newBuilder()
        .expireAfterAccess(Duration.ofMinutes(30))  // POINT: 30分アクセスなし → 自動除去
        .maximumSize(10_000)  // POINT: 最大10,000エントリ
        .build();
}

// OK: 修正策2: WeakHashMap（キーの GC で自動除去）
private final Map<String, UserSession> sessions =
    Collections.synchronizedMap(new WeakHashMap<>());
// POINT: キーへの強参照がなくなると GC 時にエントリ自動削除

// OK: 修正策3: 明示的なエビクション
@Scheduled(fixedRate = 300_000)  // 5分ごと
public void evictExpiredSessions() {
    Instant cutoff = Instant.now().minus(Duration.ofMinutes(30));
    sessions.entrySet().removeIf(e -> e.getValue().lastAccessedAt().isBefore(cutoff));
}