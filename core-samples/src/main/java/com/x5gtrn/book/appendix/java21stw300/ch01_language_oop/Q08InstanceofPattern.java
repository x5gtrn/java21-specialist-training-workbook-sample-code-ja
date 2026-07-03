package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q08InstanceofPattern implements Sample {
    public String chapter(){return "01_Language_OOP";}
    public int problem(){return 8;}
    public String title(){return "instanceof パターンマッチング";}
    public void run(){
        for (Object o : List.of(42, "hello", 3.14, List.of(1,2,3)))
            System.out.println(format(o));
    }
    private String format(Object o){
        if (o instanceof Integer i) return "int:    " + (i * 2);
        if (o instanceof String s)  return "string: " + s.toUpperCase();
        if (o instanceof Double d)  return "double: " + Math.round(d);
        return "other:  " + o;
    }
}
