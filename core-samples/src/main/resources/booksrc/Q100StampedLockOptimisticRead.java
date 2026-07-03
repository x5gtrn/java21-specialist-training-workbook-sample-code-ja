class Point {
    private double x, y;
    private final StampedLock lock = new StampedLock();

    // POINT: 楽観的読み取り（ロック不要の高速パス）
    double distanceFromOrigin() {
        long stamp = lock.tryOptimisticRead();  // POINT: ロック取得なし
        double currentX = x;  // 共有データ読み取り
        double currentY = y;

        if (!lock.validate(stamp)) {  // POINT: 読み取り中に書き込みがあったか検証
            // 楽観的読み取り失敗 → 通常の読み取りロックにフォールバック
            stamp = lock.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    // 書き込み（排他ロック）
    void move(double deltaX, double deltaY) {
        long stamp = lock.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    // POINT: ロックのアップグレード（読み取り → 書き込み）
    void moveIfAtOrigin(double newX, double newY) {
        long stamp = lock.readLock();
        try {
            while (x == 0.0 && y == 0.0) {
                long ws = lock.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;  // アップグレード成功
                    x = newX;
                    y = newY;
                    break;
                } else {
                    lock.unlockRead(stamp);
                    stamp = lock.writeLock();  // フォールバック
                }
            }
        } finally {
            lock.unlock(stamp);
        }
    }
}