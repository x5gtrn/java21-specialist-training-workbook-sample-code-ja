package com.x5gtrn.book.appendix.java21stw300.ch04_collections_generics;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SequencedMap;
import java.util.TreeMap;

/**
 * 問題47: SequencedMap のビュー（sequencedKeySet / sequencedValues）。
 * TreeMap は順序が比較器で決まるため putFirst/putLast は UnsupportedOperationException。
 */
public final class Q47SequencedMapViews implements Sample {

    public String chapter() { return "04_Collections_Generics"; }
    public int problem()    { return 47; }
    public String title()   { return "SequencedMap のビューと TreeMap.putFirst"; }

    public void run() {
        SequencedMap<String, Integer> map = new LinkedHashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        System.out.println("sequencedKeySet() = " + map.sequencedKeySet());
        System.out.println("sequencedValues() = " + map.sequencedValues());

        NavigableMap<String, Integer> tree = new TreeMap<>(Map.of("A", 1, "B", 2));
        try {
            tree.putFirst("Z", 99);
        } catch (UnsupportedOperationException e) {
            System.out.println("TreeMap.putFirst() -> " + e.getClass().getSimpleName()
                    + "  (順序は比較器が決めるため)");
        }
    }
}
