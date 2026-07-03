StampedLock lock = new StampedLock();

void outer() {
    long stamp = lock.writeLock();     // 書き込みロック取得
    try {
        inner();                       // POINT: 同じロックを再取得しようとする
    } finally {
        lock.unlockWrite(stamp);
    }
}

void inner() {
    long stamp = lock.readLock();      // POINT: 解放を永久に待つ → 自己デッドロック
    try {
        readState();
    } finally {
        lock.unlockRead(stamp);
    }
}