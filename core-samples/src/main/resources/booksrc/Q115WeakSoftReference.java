// SoftReference による画像キャッシュ
Map<String, SoftReference<BufferedImage>> cache = new ConcurrentHashMap<>();

public BufferedImage getImage(String key) {
    SoftReference<BufferedImage> ref = cache.get(key);
    BufferedImage image = (ref != null) ? ref.get() : null;

    if (image == null) {
        // キャッシュミスまたは GC により回収済み → 再読み込み
        image = loadFromDisk(key);
        cache.put(key, new SoftReference<>(image));
    }
    return image;
}

// 参照型の比較
Object obj = new Object();
// Strong: Object ref = obj;           → GC されない（到達可能な限り）
// Soft:   SoftReference<Object> ref;  → メモリ不足時のみ GC 対象
// Weak:   WeakReference<Object> ref;  → 次の GC で即回収
// Phantom: PhantomReference<Object>;  → get()は常に null、ReferenceQueue で追跡