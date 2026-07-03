package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.LinkedHashMap;
import java.util.Map;
public final class Q118CacheGrowthBound implements Sample {
    // 上限付き LRU キャッシュ: removeEldestEntry で最大サイズを超えたら最古を捨てる
    static final class LruCache<K,V> extends LinkedHashMap<K,V> {
        private final int max;
        LruCache(int max){ super(16, 0.75f, true); this.max = max; }
        protected boolean removeEldestEntry(Map.Entry<K,V> eldest){ return size() > max; }
    }
    public String chapter(){return "08_Memory_GC_Performance";}
    public int problem(){return 118;}
    public String title(){return "キャッシュ肥大とメモリ上限";}
    public void run(){
        LruCache<Integer,String> cache = new LruCache<>(3);
        for (int i=1;i<=5;i++) cache.put(i, "v" + i);   // 上限 3 を超えた分は自動退避
        System.out.println("上限3のLRU（最新3件のみ保持）= " + cache.keySet());
        // 無制限キャッシュは OutOfMemoryError の温床。必ず上限や TTL を設ける。
    }
}
