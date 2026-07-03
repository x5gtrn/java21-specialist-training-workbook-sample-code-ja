@Transactional(isolation = Isolation.READ_COMMITTED)
public AccountBalance getBalance(Long accountId) {
    return accountRepository.findBalance(accountId);
    // POINT: 他のトランザクションがコミットした変更のみ見える
    // POINT: 未コミットの変更は見えない（ダーティリード防止）
}

// ■ 分離レベルと防止される現象の対応表
//
// 分離レベル           | ダーティリード | ノンリピータブル | ファントム
// ---------------------|------------|--------------|--------
// READ_UNCOMMITTED     | 発生する    | 発生する      | 発生する
// READ_COMMITTED  POINT: | 防止 POINT: | 発生する POINT: | 発生する POINT:
// REPEATABLE_READ      | 防止       | 防止         | 発生する
// SERIALIZABLE         | 防止       | 防止         | 防止
//
// ■ 各現象の具体例
//
// ダーティリード: TX-A が未コミットの残高変更を見てしまう
// TX-B: UPDATE balance=500 (未コミット)
// TX-A: SELECT balance → 500 ← 未コミットデータ！
// TX-B: ROLLBACK → 実際は元の1000のまま
//
// ノンリピータブルリード: 同じ行を2回読むと値が変わる
// TX-A: SELECT balance → 1000
// TX-B: UPDATE balance=500; COMMIT;
// TX-A: SELECT balance → 500 ← 値が変わった！
//
// ファントムリード: 同じ条件で検索すると行数が変わる
// TX-A: SELECT COUNT(*) WHERE dept='eng' → 10人
// TX-B: INSERT INTO emp (dept='eng'); COMMIT;
// TX-A: SELECT COUNT(*) WHERE dept='eng' → 11人 ← 行が増えた！

// ■ PostgreSQL のデフォルト: READ_COMMITTED
// ■ MySQL (InnoDB) のデフォルト: REPEATABLE_READ