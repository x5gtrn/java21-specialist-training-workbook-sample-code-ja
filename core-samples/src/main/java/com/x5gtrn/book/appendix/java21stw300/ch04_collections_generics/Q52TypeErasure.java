package com.x5gtrn.book.appendix.java21stw300.ch04_collections_generics;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayList;
import java.util.List;
public final class Q52TypeErasure implements Sample {
    public String chapter(){return "04_Collections_Generics";}
    public int problem(){return 52;}
    public String title(){return "型消去（Type Erasure）の実践的影響";}
    public void run(){
        List<String> strings = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        // 実行時にはどちらも java.util.ArrayList（型引数は消える）
        System.out.println("同じ実行時クラス? -> " + (strings.getClass() == integers.getClass()));
        System.out.println("getClass() = " + strings.getClass().getName());
        // new T[] や obj instanceof List<String> は型消去のため不可。
    }
}
