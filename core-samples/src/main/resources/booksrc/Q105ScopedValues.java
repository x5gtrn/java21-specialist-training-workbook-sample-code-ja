// ScopedValue の宣言と使用
private static final ScopedValue<UserContext> CURRENT_USER = ScopedValue.newInstance();

// スコープの設定と実行
public void handleRequest(HttpRequest request) {
    UserContext user = authenticate(request);

    ScopedValue.where(CURRENT_USER, user).run(() -> {
        // POINT: このスコープ内では CURRENT_USER が利用可能
        processOrder();  // CURRENT_USER.get() で取得可能
        sendNotification();  // 呼び出し先でも同じ値が取得可能
    });
    // ← スコープ終了: CURRENT_USER のバインディングは自動解除
}

private void processOrder() {
    UserContext user = CURRENT_USER.get();  // POINT: スコープ内でのみ取得可能
    System.out.println("Processing for: " + user.name());
}

// ThreadLocal との比較
// ThreadLocal: set() → 使用 → remove() が必要（リーク防止）
// ScopedValue: where().run() → 自動的にスコープ終了時にクリーンアップ