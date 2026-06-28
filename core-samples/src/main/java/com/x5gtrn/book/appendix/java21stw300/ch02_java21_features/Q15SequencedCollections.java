package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayList;
import java.util.List;

/**
 * 問題15: SequencedCollection（JEP 431, Java 21 正式）。
 * getFirst / getLast / addFirst / addLast / reversed で先頭末尾を統一的に扱える。
 */
public final class Q15SequencedCollections implements Sample {

    public String chapter() { return "02_Java21_Features"; }
    public int problem()    { return 15; }
    public String title()   { return "Sequenced Collections の先頭末尾操作"; }

    public void run() {
        List<String> list = new ArrayList<>(List.of("A", "B", "C"));
        System.out.println("getFirst() = " + list.getFirst());
        System.out.println("getLast()  = " + list.getLast());

        list.addFirst("Z");
        System.out.println("addFirst(Z) -> " + list);
        list.addLast("D");
        System.out.println("addLast(D)  -> " + list);
        System.out.println("reversed()  -> " + list.reversed());
    }
}
