package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q104VirtualThreadThreadLocal implements Sample {
    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 104;}
    public String title(){return "仮想スレッドと ThreadLocal の使いすぎ";}
    public void run() throws InterruptedException {
        Thread vt = Thread.ofVirtual().start(() -> {
            REQUEST_ID.set("req-42");
            try { System.out.println("ThreadLocal 値 = " + REQUEST_ID.get()); }
            finally { REQUEST_ID.remove(); } // 必ず remove（大量の仮想スレッドではメモリを圧迫）
        });
        vt.join();
        // 数百万の仮想スレッド × ThreadLocal はヒープを消費する。ScopedValue（不変・自動破棄）が推奨。
        System.out.println("大量の仮想スレッドでは ThreadLocal より ScopedValue を検討する");
    }
}
