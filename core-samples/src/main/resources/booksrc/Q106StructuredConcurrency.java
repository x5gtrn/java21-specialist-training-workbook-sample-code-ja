// Structured Concurrency の基本パターン（Preview API）
record UserData(String name, String email) {}
record OrderHistory(List<String> orders) {}

UserData userData;
OrderHistory orderHistory;

try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    // POINT: サブタスクを仮想スレッドでフォーク
    Subtask<UserData> userTask = scope.fork(() -> fetchUserData(userId));
    Subtask<OrderHistory> orderTask = scope.fork(() -> fetchOrderHistory(userId));

    // POINT: すべてのサブタスクの完了を待機
    scope.join();  // ブロック: 全タスク完了まで待機
    scope.throwIfFailed();  // いずれかが失敗していたら例外をスロー

    // POINT: 結果の取得
    userData = userTask.get();  // 成功保証済みの安全な取得
    orderHistory = orderTask.get();
}
// ← スコープ終了: すべてのサブタスクは確実に完了している

// ShutdownOnSuccess: 最初の成功結果で他をキャンセル
try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
    scope.fork(() -> queryPrimaryDb(id));
    scope.fork(() -> queryReplicaDb(id));
    scope.join();
    String firstResult = scope.result();  // 最初に成功した結果
}