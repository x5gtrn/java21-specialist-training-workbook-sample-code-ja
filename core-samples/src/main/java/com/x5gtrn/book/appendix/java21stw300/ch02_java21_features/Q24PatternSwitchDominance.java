package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q24PatternSwitchDominance implements Sample {
    sealed interface Vehicle permits Car, Truck {}
    record Car(int seats) implements Vehicle {}
    record Truck(double tons) implements Vehicle {}
    public String chapter(){return "02_Java21_Features";}
    public int problem(){return 24;}
    public String title(){return "パターンマッチング switch の支配関係";}
    public void run(){
        for (Vehicle v : List.of(new Car(2), new Car(5), new Truck(10)))
            System.out.println(v + " -> " + label(v));
        // より限定的な case を先に置く。一般的な case を先に置くと
        // 後続が到達不能となり "dominated by a preceding case label" でコンパイルエラー。
    }
    private String label(Vehicle v){
        return switch (v) {
            case Car c when c.seats() <= 2 -> "スポーツカー";
            case Car c -> "乗用車";
            case Truck t -> "トラック(" + t.tons() + "t)";
        };
    }
}
