// ■ インターフェース定義のみ → 実装クラスは Spring が自動生成
public interface UserRepository extends JpaRepository<User, Long> {

    // メソッド名からクエリ自動導出（Derived Query）
    List<User> findByEmailContaining(String keyword);
    Optional<User> findByUsernameAndActiveTrue(String username);
    List<User> findByAgeGreaterThanEqual(int minAge);
    long countByDepartment(String department);
    void deleteByActiveIsFalse();
    boolean existsByEmail(String email);

    // カスタム JPQL
    @Query("SELECT u FROM User u WHERE u.createdAt > :since AND u.active = true")
    List<User> findActiveUsersSince(@Param("since") LocalDateTime since);

    // ネイティブ SQL
    @Query(value = "SELECT * FROM users WHERE email ~* :pattern", nativeQuery = true)
    List<User> findByEmailRegex(@Param("pattern") String pattern);

    // ページネーション + ソート
    Page<User> findByActive(boolean active, Pageable pageable);
    // 使用: userRepo.findByActive(true, PageRequest.of(0, 20, Sort.by("name")))

    // Slice（次ページの有無だけ判定、全件数カウント不要で高速）
    Slice<User> findByDepartment(String dept, Pageable pageable);
}

// ■ JpaRepository が提供する標準メソッド（実装不要）
// save(entity) / saveAll(entities) / findById(id) / existsById(id)
// findAll() / findAll(Pageable) / findAll(Sort) / findAllById(ids)
// count() / deleteById(id) / delete(entity) / deleteAll()
// flush() / saveAndFlush(entity)

// ■ メソッド名規約のキーワード
// findBy / countBy / existsBy / deleteBy — プレフィックス
// And / Or — 条件結合
// Is / Equals / Not / IsNot — 比較
// LessThan / GreaterThan / Between — 範囲
// Like / Containing / StartingWith / EndingWith — 文字列
// In / NotIn — コレクション
// True / False / Null / NotNull — 状態
// OrderBy...Asc / Desc — ソート