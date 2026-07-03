package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.function.Supplier;
public final class Q114ClosureReachability implements Sample {
    public String chapter(){return "08_Memory_GC_Performance";}
    public int problem(){return 114;}
    public String title(){return "クロージャによるオブジェクト到達可能性";}
    public void run(){
        Supplier<Integer> supplier = makeSupplier();
        System.out.println("クロージャの結果 = " + supplier.get());
        // ラムダは捕捉した変数への参照を保持するため、その変数が指すオブジェクトは
        // ラムダが生存する限り GC されない。巨大オブジェクトの不要な捕捉はリーク要因。
        System.out.println("ラムダが生きている間、捕捉オブジェクトは到達可能のまま（不要な捕捉に注意）");
    }
    private Supplier<Integer> makeSupplier(){
        int[] captured = new int[1000];               // ラムダに捕捉される配列
        captured[0] = 42;
        return () -> captured[0];                      // captured は返却後も到達可能
    }
}
