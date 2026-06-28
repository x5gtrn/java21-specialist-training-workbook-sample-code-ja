package com.x5gtrn.book.appendix.java21stw300.ch04_collections_generics;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 問題46: 不変化の3手段の違い。
 * - Collections.unmodifiableList(): 元リストのビュー（元の変更が反映される）
 * - List.copyOf(): 独立したコピー（元の変更は反映されない）
 * - List.of(): 引数から直接作る不変リスト（add は UnsupportedOperationException）
 */
public final class Q46ViewVsCopy implements Sample {

    public String chapter() { return "04_Collections_Generics"; }
    public int problem()    { return 46; }
    public String title()   { return "unmodifiableList(ビュー) vs copyOf(コピー)"; }

    public void run() {
        List<String> original = new ArrayList<>(Arrays.asList("A", "B", "C"));
        List<String> unmod = Collections.unmodifiableList(original);
        original.add("D");
        System.out.println("unmodifiableList (ビュー) : " + unmod + "  <- 元の変更が反映される");

        List<String> base = new ArrayList<>(Arrays.asList("A", "B", "C"));
        List<String> copied = List.copyOf(base);
        base.add("D");
        System.out.println("List.copyOf (コピー)       : " + copied + "  <- 元の変更は反映されない");

        List<String> factory = List.of("A", "B", "C");
        try {
            factory.add("D");
        } catch (UnsupportedOperationException e) {
            System.out.println("List.of().add()           : " + e.getClass().getSimpleName());
        }
    }
}
