package com.x5gtrn.book.appendix.java21stw300.ch03_exception_handling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q38TryWithResourcesEffectivelyFinal implements Sample {
    static final class Resource implements AutoCloseable {
        private final String name;
        Resource(String name){ this.name = name; System.out.println("  open " + name); }
        void use(){ System.out.println("  use " + name); }
        public void close(){ System.out.println("  close " + name); }
    }
    public String chapter(){ return "03_Exception_Handling"; }
    public int problem(){ return 38; }
    public String title(){ return "try-with-resources と effectively final"; }
    public void run(){
        Resource r = new Resource("A");   // 事前に確定した実質final変数
        // Java 9+ では既存の（実質 final な）変数を try-with-resources に直接指定できる
        try (r) {
            r.use();
        }
        System.out.println("close は宣言と逆順・例外発生時も保証。抑制例外は getSuppressed() で取得可能");
    }
}
