package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q03EnumAbstractMethod implements Sample {
    enum Operation {
        PLUS  { public int apply(int a,int b){return a+b;} },
        MINUS { public int apply(int a,int b){return a-b;} },
        TIMES { public int apply(int a,int b){return a*b;} },
        DIVIDE{ public int apply(int a,int b){return a/b;} };
        public abstract int apply(int a,int b);
    }
    public String chapter(){return "01_Language_OOP";}
    public int problem(){return 3;}
    public String title(){return "enum の抽象メソッド";}
    public void run(){
        for (Operation op : Operation.values())
            System.out.printf("%-6s 12,4 -> %d%n", op, op.apply(12,4));
    }
}
