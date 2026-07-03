package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q116LazyInitialization implements Sample {
    static final class Expensive {
        Expensive(){ System.out.println("  Expensive を生成（初回アクセス時のみ）"); }
        String value(){ return "ready"; }
    }
    // Initialization-on-demand holder: クラス初期化の遅延と可視性をJVMが保証（同期不要）
    static final class Holder { static final Expensive INSTANCE = new Expensive(); }
    static Expensive get(){ return Holder.INSTANCE; }
    public String chapter(){return "08_Memory_GC_Performance";}
    public int problem(){return 116;}
    public String title(){return "Lazy Initialization パターン";}
    public void run(){
        System.out.println("get() 呼び出し前（まだ生成されていない）");
        System.out.println("1回目 get() = " + get().value());
        System.out.println("2回目 get() = " + get().value() + "（生成は 1 回だけ）");
    }
}
