package com.x5gtrn.book.appendix.java21stw300.ch04_collections_generics;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public final class Q54EqualsHashCodeMutableKey implements Sample {
    static final class MutablePoint {
        int x, y;
        MutablePoint(int x, int y){ this.x = x; this.y = y; }
        public boolean equals(Object o){ return o instanceof MutablePoint p && p.x == x && p.y == y; }
        public int hashCode(){ return Objects.hash(x, y); }
        public String toString(){ return "(" + x + "," + y + ")"; }
    }
    public String chapter(){return "04_Collections_Generics";}
    public int problem(){return 54;}
    public String title(){return "equals/hashCode と可変キーの危険性";}
    public void run(){
        Map<MutablePoint, String> map = new HashMap<>();
        MutablePoint key = new MutablePoint(1, 2);
        map.put(key, "value");
        System.out.println("変更前 get(key) = " + map.get(key));
        key.x = 99; // キーを変更 -> hashCode が変わりバケットを見失う
        System.out.println("キー変更後 get(key) = " + map.get(key) + "（見つからない）");
        System.out.println("containsKey(new (99,2)) = " + map.containsKey(new MutablePoint(99, 2)));
    }
}
