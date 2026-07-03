package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q30VirtualThreadThreadLocalMdc implements Sample {
    private static final ThreadLocal<String> CTX = new ThreadLocal<>();
    public String chapter(){return "02_Java21_Features";}
    public int problem(){return 30;}
    public String title(){return "仮想スレッドと ThreadLocal/MDC の注意点";}
    public void run() throws InterruptedException {
        CTX.set("親スレッドの値");
        // 仮想スレッドは親の ThreadLocal を継承しない（InheritableThreadLocal でない限り）
        Thread child = Thread.ofVirtual().start(() -> System.out.println("子スレッドの CTX = " + CTX.get()));
        child.join();
        System.out.println("親スレッドの CTX = " + CTX.get());
        CTX.remove();
        // MDC もスレッド束縛のため、仮想スレッド/非同期境界では明示的な伝播が必要。
        // 大量の仮想スレッドでは ThreadLocal の多用がメモリを圧迫するため ScopedValue が推奨。
        System.out.println("MDC/ThreadLocal は境界を越えて自動伝播しない → 明示伝播 or ScopedValue");
    }
}
