package com.x5gtrn.book.appendix.java21stw300.ch04_collections_generics;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q49BoundedTypeParameter implements Sample {
    public String chapter(){return "04_Collections_Generics";}
    public int problem(){return 49;}
    public String title(){return "ジェネリクスの境界型パラメータ";}
    // T extends Comparable<T> により compareTo が使える
    static <T extends Comparable<T>> T max(List<T> list){
        T m = list.get(0);
        for (T x : list) if (x.compareTo(m) > 0) m = x;
        return m;
    }
    public void run(){
        System.out.println("max(int)    = " + max(List.of(3, 9, 1, 7)));
        System.out.println("max(String) = " + max(List.of("banana", "apple", "cherry")));
    }
}
