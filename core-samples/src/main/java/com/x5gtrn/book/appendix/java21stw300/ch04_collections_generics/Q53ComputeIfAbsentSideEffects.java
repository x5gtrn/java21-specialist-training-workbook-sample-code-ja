package com.x5gtrn.book.appendix.java21stw300.ch04_collections_generics;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public final class Q53ComputeIfAbsentSideEffects implements Sample {
    public String chapter(){return "04_Collections_Generics";}
    public int problem(){return 53;}
    public String title(){return "computeIfAbsent と副作用の扱い";}
    public void run(){
        // グルーピング（マルチマップ）を computeIfAbsent で簡潔に構築
        Map<Integer, List<String>> byLength = new LinkedHashMap<>();
        for (String w : List.of("a", "bb", "cc", "ddd", "e"))
            byLength.computeIfAbsent(w.length(), k -> new ArrayList<>()).add(w);
        System.out.println(byLength);
        // マッピング関数内で同じマップを変更すると ConcurrentModificationException になり得る点に注意。
    }
}
