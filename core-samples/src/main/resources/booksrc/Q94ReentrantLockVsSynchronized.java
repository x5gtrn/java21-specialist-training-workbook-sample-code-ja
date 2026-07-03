ReentrantLock lock = new ReentrantLock();

// tryLock with timeout — ReentrantLock の大きな利点
public boolean transfer(Account from, Account to, BigDecimal amount) {
    try {
        if (lock.tryLock(5, TimeUnit.SECONDS)) {  // POINT: タイムアウト付き
            try {
                from.debit(amount);
                to.credit(amount);
                return true;
            } finally {
                lock.unlock();  // POINT: 明示的な unlock が必須
            }
        } else {
            log.warn("Could not acquire lock within timeout");
            return false;  // タイムアウト → 別の処理に移行可能
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return false;
    }
}

// synchronized では同等の機能がない
public synchronized void transferSync(Account from, Account to, BigDecimal amount) {
    // POINT: ロック取得まで無期限にブロック（タイムアウト不可）
    from.debit(amount);
    to.credit(amount);
}