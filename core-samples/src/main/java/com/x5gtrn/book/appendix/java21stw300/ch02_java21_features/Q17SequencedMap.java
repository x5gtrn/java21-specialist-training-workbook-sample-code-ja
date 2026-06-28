package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.LinkedHashMap;
import java.util.SequencedMap;

/**
 * 問題17: SequencedMap（JEP 431）。
 * reversed() で逆順ビュー、firstEntry / lastEntry、putFirst / putLast が使える。
 */
public final class Q17SequencedMap implements Sample {

    public String chapter() { return "02_Java21_Features"; }
    public int problem()    { return 17; }
    public String title()   { return "SequencedMap と逆順ビュー"; }

    public void run() {
        SequencedMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        System.out.println("keySet()            = " + map.keySet());
        System.out.println("reversed().keySet() = " + map.reversed().keySet());
        System.out.println("firstEntry()        = " + map.firstEntry());
        System.out.println("lastEntry()         = " + map.lastEntry());

        map.putFirst("z", 0);
        System.out.println("putFirst(z,0)       = " + map);
    }
}
