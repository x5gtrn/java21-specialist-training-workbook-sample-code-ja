// ■ JPA エンティティの4つのライフサイクル状態
//
// Transient（新規）→ persist() → Managed（管理）
// ↕ flush/commit で自動同期
// Managed → detach()/clear() → Detached（分離）
// Detached → merge() → Managed（再管理）
// Managed → remove() → Removed（削除予定）
// Removed → flush/commit → データベースから削除

User user = new User("Alice");  // Transient: DB に対応行なし
entityManager.persist(user);  // Managed: 変更が自動追跡される

user.setEmail("alice@new.com");  // POINT: Managed 中の変更は自動検知（ダーティチェック）
// → flush/commit 時に UPDATE が自動実行される

entityManager.detach(user);  // Detached: 追跡から外れる
user.setEmail("alice@changed.com");  // POINT: この変更は DB に反映されない！

User merged = entityManager.merge(user);  // POINT: merge()で再管理
// merged は Managed 状態の新しいインスタンス（user とは別オブジェクト）
// merged への変更は追跡される

// ■ Spring Data JPA での実務パターン
@Transactional
public User updateUser(Long id, UpdateRequest req) {
    User user = userRepository.findById(id).orElseThrow();  // 管理状態
    user.setEmail(req.email());  // ダーティチェック → 自動 UPDATE
    // save()を呼ばなくてもトランザクション終了時に反映される
    return user;
}