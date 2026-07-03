package com.x5gtrn.book.appendix.java21stw300.ch04_collections_generics;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayList;
import java.util.List;
public final class Q51PecsPrinciple implements Sample {
    public String chapter(){return "04_Collections_Generics";}
    public int problem(){return 51;}
    public String title(){return "PECS 原則の実践";}
    // Producer Extends, Consumer Super: src から読み dst へ書く
    static <T> void copy(List<? extends T> src, List<? super T> dst){
        for (T t : src) dst.add(t);
    }
    public void run(){
        List<Integer> src = List.of(1, 2, 3);
        List<Number> dst = new ArrayList<>();
        copy(src, dst); // Integer を Number リストへ
        System.out.println("copy 後 dst = " + dst);
    }
}
