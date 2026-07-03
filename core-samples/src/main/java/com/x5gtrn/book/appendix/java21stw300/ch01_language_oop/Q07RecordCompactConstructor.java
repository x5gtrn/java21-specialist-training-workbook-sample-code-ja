package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q07RecordCompactConstructor implements Sample {
    record Range(int low, int high) {
        Range { // コンパクトコンストラクタで不変条件を検証・正規化
            if (low > high) throw new IllegalArgumentException("low > high: " + low + " > " + high);
        }
        int span(){ return high - low; }
    }
    public String chapter(){return "01_Language_OOP";}
    public int problem(){return 7;}
    public String title(){return "record のコンパクトコンストラクタと不変条件";}
    public void run(){
        var r = new Range(3, 10);
        System.out.println("Range(3,10) span=" + r.span());
        try { new Range(10, 3); }
        catch (IllegalArgumentException e){ System.out.println("不変条件違反 -> " + e.getMessage()); }
    }
}
