package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayList;
import java.util.List;

/**
 * 問題18: 可変リストでは addFirst/addLast が動作するが、List.of() の不変リストでは
 * UnsupportedOperationException がスローされる。reversed() はビューを返す。
 */
public final class Q18ImmutableUnsupported implements Sample {

    public String chapter() { return "02_Java21_Features"; }
    public int problem()    { return 18; }
    public String title()   { return "不変コレクションと UnsupportedOperationException"; }

    public void run() {
        List<String> mutable = new ArrayList<>(List.of("A", "B", "C"));
        mutable.addFirst("Z");
        mutable.addLast("D");
        System.out.println("可変リスト addFirst/addLast -> " + mutable);

        List<String> immutable = List.of("A", "B", "C");
        System.out.println("不変リスト getFirst = " + immutable.getFirst()
                + ", getLast = " + immutable.getLast()
                + ", reversed = " + immutable.reversed());
        try {
            immutable.addFirst("Z");
        } catch (UnsupportedOperationException e) {
            System.out.println("不変リスト addFirst -> " + e.getClass().getSimpleName());
        }
    }
}
