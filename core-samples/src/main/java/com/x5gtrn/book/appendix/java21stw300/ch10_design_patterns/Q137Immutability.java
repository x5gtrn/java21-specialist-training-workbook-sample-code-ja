package com.x5gtrn.book.appendix.java21stw300.ch10_design_patterns;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q137Immutability implements Sample {
    // 不変オブジェクト: 変更は新しいインスタンスを返す（with 系メソッド）
    record Money(long amount, String currency) {
        Money plus(long delta){ return new Money(amount + delta, currency); }
    }
    public String chapter(){return "10_Design_Patterns";}
    public int problem(){return 137;}
    public String title(){return "不変性パターン";}
    public void run(){
        Money original = new Money(1000, "JPY");
        Money increased = original.plus(500);
        System.out.println("original  = " + original + "（変化しない）");
        System.out.println("increased = " + increased);
        List<Integer> immutable = List.of(1, 2, 3);
        try { immutable.add(4); }
        catch (UnsupportedOperationException e){ System.out.println("List.of は不変 -> " + e.getClass().getSimpleName()); }
    }
}
