package com.x5gtrn.book.appendix.java21stw300.ch11_architecture;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q148BoundedContext implements Sample {
    // 同じ「Customer」でも文脈ごとに意味・属性が異なる → 文脈ごとに別モデルを持つ
    static final class SalesCustomer { final String id; final String creditRating;
        SalesCustomer(String id, String cr){ this.id = id; this.creditRating = cr; } }
    static final class SupportCustomer { final String id; final int openTickets;
        SupportCustomer(String id, int t){ this.id = id; this.openTickets = t; } }
    public String chapter(){ return "11_Architecture"; }
    public int problem(){ return 148; }
    public String title(){ return "Bounded Context と共有モデル"; }
    public void run(){
        System.out.println("販売context の Customer  : id=C1, 与信=" + new SalesCustomer("C1","A").creditRating);
        System.out.println("サポートcontext の Customer: id=C1, 未対応=" + new SupportCustomer("C1",3).openTickets + "件");
        System.out.println("同一IDでも文脈で必要な属性/振る舞いが違う。単一の巨大共有モデルは結合を生む");
        System.out.println("境界を跨ぐ連携は ID と最小限の翻訳(Anti-Corruption Layer)で行い、モデルを混ぜない");
    }
}
