// デッドロックを引き起こすコードパターン

// NG: デッドロック発生の典型例
public void transfer(Account from, Account to, BigDecimal amount) {
    synchronized (from) {  // POINT: スレッド1: Account A をロック
        synchronized (to) {  // POINT: スレッド1: Account B のロックを待機
            from.debit(amount);  // しかしスレッド2が B をロック中
            to.credit(amount);
        }
    }
}
// スレッド1: transfer(accountA, accountB, ...) → A lock → B 待ち
// スレッド2: transfer(accountB, accountA, ...) → B lock → A 待ち
// → デッドロック！

// OK: 解決策1: ロック順序の統一
public void transferSafe(Account from, Account to, BigDecimal amount) {
    Account first = from.getId() < to.getId() ? from : to;
    Account second = from.getId() < to.getId() ? to : from;
    synchronized (first) {  // POINT: ID が小さい方を常に先にロック
        synchronized (second) {
            from.debit(amount);
            to.credit(amount);
        }
    }
}

// OK: 解決策2: tryLock でタイムアウト
public boolean transferWithLock(Account from, Account to, BigDecimal amount) {
    try {
        if (from.getLock().tryLock(1, TimeUnit.SECONDS)) {
            try {
                if (to.getLock().tryLock(1, TimeUnit.SECONDS)) {
                    try {
                        from.debit(amount);
                        to.credit(amount);
                        return true;
                    } finally { to.getLock().unlock(); }
                }
            } finally { from.getLock().unlock(); }
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    return false;  // タイムアウト → リトライまたは別処理
}