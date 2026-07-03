package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.lang.ref.WeakReference;
public final class Q115WeakSoftReference implements Sample {
    public String chapter(){return "08_Memory_GC_Performance";}
    public int problem(){return 115;}
    public String title(){return "WeakReference と SoftReference";}
    public void run() throws InterruptedException {
        Object obj = new Object();
        WeakReference<Object> weak = new WeakReference<>(obj);
        System.out.println("強参照あり: weak.get() != null -> " + (weak.get() != null));
        obj = null;                                    // 強参照を除去
        System.gc(); Thread.sleep(50);                 // GC を促す（保証はされない）
        System.out.println("強参照除去+GC後: weak.get() == null -> " + (weak.get() == null));
        // WeakReference は GC 対象を妨げない。SoftReference はメモリ逼迫時のみ回収されキャッシュ向き。
    }
}
