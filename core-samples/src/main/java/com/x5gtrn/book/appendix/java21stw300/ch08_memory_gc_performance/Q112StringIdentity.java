package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;

/**
 * 問題112: new String(...) は毎回別オブジェクトを生成するため == は false。
 * intern() 後は同じプールインスタンスを指すため == は true。
 */
public final class Q112StringIdentity implements Sample {

    public String chapter() { return "08_Memory_GC_Performance"; }
    public int problem()    { return 112; }
    public String title()   { return "new String の同一性と intern()"; }

    public void run() {
        String city1 = new String("Tokyo");
        String city2 = new String("Tokyo");
        System.out.println("new String == new String   : " + (city1 == city2));

        String interned1 = city1.intern();
        String interned2 = city2.intern();
        System.out.println("intern() == intern()       : " + (interned1 == interned2));
        System.out.println("equals (値の比較)          : " + city1.equals(city2));
    }
}
