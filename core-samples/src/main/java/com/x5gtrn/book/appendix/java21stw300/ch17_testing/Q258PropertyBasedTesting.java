package com.x5gtrn.book.appendix.java21stw300.ch17_testing;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public final class Q258PropertyBasedTesting implements Sample {
    public String chapter(){ return "17_Testing"; }
    public int problem(){ return 258; }
    public String title(){ return "Property-Based Testing"; }
    public void run(){
        // 例示ベースでなく『性質』を無作為な多数入力で検証する
        Random rnd = new Random(42);
        boolean holds = true;
        for (int t = 0; t < 1000 && holds; t++) {
            List<Integer> list = rnd.ints(rnd.nextInt(10), 0, 100).boxed().collect(java.util.stream.Collectors.toList());
            List<Integer> twice = new ArrayList<>(list);
            Collections.reverse(twice); Collections.reverse(twice);
            if (!twice.equals(list)) holds = false;  // 性質: reverse を2回で元に戻る
        }
        System.out.println("性質『reverse∘reverse = 恒等』を1000ケースで検証 -> " + (holds ? "成立" : "反例発見"));
        System.out.println("PBT の強み: 人が思いつかない境界(空/巨大/重複/負値)を自動探索し反例を提示");
        System.out.println("縮小(shrinking): 反例を最小形へ自動簡約して原因特定を助ける（jqwik 等）");
    }
}
