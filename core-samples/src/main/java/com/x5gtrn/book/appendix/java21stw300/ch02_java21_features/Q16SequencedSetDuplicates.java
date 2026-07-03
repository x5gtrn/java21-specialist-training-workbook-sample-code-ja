package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.LinkedHashSet;
import java.util.SequencedSet;
public final class Q16SequencedSetDuplicates implements Sample {
    public String chapter(){return "02_Java21_Features";}
    public int problem(){return 16;}
    public String title(){return "SequencedSet と重複要素";}
    public void run(){
        SequencedSet<String> set = new LinkedHashSet<>();
        set.add("A"); set.add("B"); set.add("C");
        System.out.println("add(A)（重複）-> " + set.add("A") + "、集合は変化なし: " + set);
        System.out.println("getFirst=" + set.getFirst() + ", getLast=" + set.getLast());
        System.out.println("reversed=" + set.reversed());
        set.addFirst("A"); // 既存要素を先頭へ移動
        System.out.println("addFirst(A) で先頭へ移動 -> " + set);
    }
}
