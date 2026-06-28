package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;

/**
 * 問題22: switch パターンの when ガードは上から順に評価される。
 * より限定的な case を先に置く。順序を逆にすると "dominated by a preceding case label"
 * というコンパイルエラーになる。
 */
public final class Q22PatternSwitchGuards implements Sample {

    sealed interface Shipment permits Domestic, International {}
    record Domestic(double weight) implements Shipment {}
    record International(double weight, String country) implements Shipment {}

    public String chapter() { return "02_Java21_Features"; }
    public int problem()    { return 22; }
    public String title()   { return "パターンマッチング switch の when ガード順序"; }

    public void run() {
        List<Shipment> shipments = List.of(
            new Domestic(40),
            new Domestic(10),
            new International(5, "JP"),
            new International(5, "US")
        );
        for (Shipment s : shipments) {
            double cost = switch (s) {
                // より限定的な条件を先に置く
                case Domestic d when d.weight() > 30 -> d.weight() * 2.0;
                case Domestic d                      -> d.weight() * 1.0;
                case International i when "JP".equals(i.country()) -> i.weight() * 1.5;
                case International i                  -> i.weight() * 3.0;
            };
            System.out.printf("%-28s cost=%.1f%n", s, cost);
        }
        // 注: case Domestic d を先に置くと、後続の
        //     case Domestic d when ... は到達不能となりコンパイルエラー。
    }
}
