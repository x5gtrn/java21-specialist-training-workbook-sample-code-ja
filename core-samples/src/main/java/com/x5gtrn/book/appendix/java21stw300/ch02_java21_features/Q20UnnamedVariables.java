package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 問題20: 無名変数 '_'（JEP 443, Preview）。
 * catch 句・ラムダ・switch パターン・拡張 for 文で「使わない変数」を '_' で示せる。
 * コンパイル・実行には --enable-preview が必要。
 */
public final class Q20UnnamedVariables implements Sample {

    sealed interface Shape permits Circle, Rectangle {}
    record Circle(double radius) implements Shape {}
    record Rectangle(double w, double h) implements Shape {}

    public String chapter() { return "02_Java21_Features"; }
    public int problem()    { return 20; }
    public String title()   { return "無名変数 '_'（Preview）"; }
    @Override public boolean preview() { return true; }

    public void run() {
        // 1. catch 句: 例外オブジェクトが不要
        try {
            Integer.parseInt("not-a-number");
        } catch (NumberFormatException _) {
            System.out.println("catch (NumberFormatException _) : 例外は使わずログだけ");
        }

        // 2. ラムダ: キーが不要
        Map<String, Integer> map = new LinkedHashMap<>(Map.of("x", 10));
        map.forEach((_, value) -> System.out.println("forEach((_, value)) : value=" + value));

        // 3. switch パターン: 型だけ判定
        for (Shape shape : List.of(new Circle(1.0), new Rectangle(2.0, 3.0))) {
            String desc = switch (shape) {
                case Circle _    -> "円（半径は不要）";
                case Rectangle _ -> "長方形（幅高さは不要）";
            };
            System.out.println("switch case X _ : " + desc);
        }

        // 4. 拡張 for 文: 要素は不要、回数だけ
        int count = 0;
        for (var _ : List.of("a", "b", "c")) {
            count++;
        }
        System.out.println("for (var _ : ...) : count=" + count);
    }
}
