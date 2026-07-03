package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayList;
import java.util.List;
public final class Q117StaticCollectionLeak implements Sample {
    // static コレクションはクラスと同じ寿命。追加した参照を除去しないと回収されずリークする。
    private static final List<byte[]> CACHE = new ArrayList<>();
    public String chapter(){return "08_Memory_GC_Performance";}
    public int problem(){return 117;}
    public String title(){return "静的コレクションとリスナーによるメモリリーク";}
    public void run(){
        for (int i=0;i<5;i++) CACHE.add(new byte[1024]);
        System.out.println("static CACHE 保持数 = " + CACHE.size() + "（このままだと永続的に残る）");
        CACHE.clear();                                 // 不要になったら明示的に除去（リスナーも removeListener）
        System.out.println("clear 後 = " + CACHE.size() + "（参照を切って GC 可能にする）");
    }
}
