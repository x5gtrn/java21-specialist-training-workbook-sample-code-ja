package com.x5gtrn.book.appendix.java21stw300.ch04_collections_generics;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q50InvarianceExtendsWildcard implements Sample {
    public String chapter(){return "04_Collections_Generics";}
    public int problem(){return 50;}
    public String title(){return "ジェネリクスの不変性と extends ワイルドカード";}
    // List<Integer> は List<Number> のサブタイプではない（不変）。読み取りには ? extends を使う
    static double sum(List<? extends Number> nums){
        double s = 0; for (Number n : nums) s += n.doubleValue(); return s;
    }
    public void run(){
        List<Integer> ints = List.of(1, 2, 3);
        List<Double> doubles = List.of(1.5, 2.5);
        System.out.println("sum(ints)    = " + sum(ints));
        System.out.println("sum(doubles) = " + sum(doubles));
        // ? extends Number からは取り出せるが add はできない（プロデューサー）
    }
}
