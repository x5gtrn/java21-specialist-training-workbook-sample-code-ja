package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayList;
import java.util.Map;
public final class Q02VarTypeInference implements Sample {
    public String chapter(){return "01_Language_OOP";}
    public int problem(){return 2;}
    public String title(){return "ローカル変数型推論（var）";}
    public void run(){
        var list = new ArrayList<String>();
        list.add("A"); list.add("B");
        var count = 42;               // int
        var ratio = 3.14;             // double
        var map = Map.of("k", 1);     // Map<String,Integer>
        System.out.println("list -> " + list.getClass().getSimpleName() + " " + list);
        System.out.println("count -> " + ((Object)count).getClass().getSimpleName() + " = " + count);
        System.out.println("ratio -> " + ((Object)ratio).getClass().getSimpleName() + " = " + ratio);
        System.out.println("map -> " + map);
        for (var s : list) System.out.println("拡張for var -> " + s);
        // var は初期化子が必須。フィールド・メソッド引数・戻り値型には使えない。
    }
}
