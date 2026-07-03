package com.x5gtrn.book.appendix.java21stw300.ch10_design_patterns;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
public final class Q139StrategyWithLambda implements Sample {
    public String chapter(){return "10_Design_Patterns";}
    public int problem(){return 139;}
    public String title(){return "Strategy パターンと Lambda";}
    public void run(){
        // 戦略をクラス階層でなくラムダで表現し、Map で切り替える
        Map<String, DoubleUnaryOperator> discounts = Map.of(
            "none", price -> price,
            "member", price -> price * 0.9,
            "vip", price -> price * 0.8);
        double base = 1000;
        for (String plan : new String[]{"none","member","vip"})
            System.out.printf("%-7s -> %.0f円%n", plan, discounts.get(plan).applyAsDouble(base));
    }
}
