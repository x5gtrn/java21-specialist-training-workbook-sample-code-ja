package com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;

/**
 * 問題110: String.intern() は文字列プール内の同一参照を返す。
 * new String("hello").intern() は リテラル "hello" と == で一致する。
 */
public final class Q110StringIntern implements Sample {

    public String chapter() { return "08_Memory_GC_Performance"; }
    public int problem()    { return 110; }
    public String title()   { return "String.intern() と文字列プール"; }

    public void run() {
        String s1 = new String("hello").intern();
        String s2 = "hello";
        System.out.println("new String(\"hello\").intern() == \"hello\" : " + (s1 == s2));

        // Compact Strings: Latin-1 は 1 バイト/文字、それ以外は UTF-16 で 2 バイト/文字
        System.out.println("\"Hello\" は Latin-1 (1byte/char), \"こんにちは\" は UTF-16 (2byte/char) で内部表現される");
    }
}
