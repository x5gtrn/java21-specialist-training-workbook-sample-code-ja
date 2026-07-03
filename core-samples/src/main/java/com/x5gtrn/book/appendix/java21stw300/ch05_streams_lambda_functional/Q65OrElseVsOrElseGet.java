package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Optional;
public final class Q65OrElseVsOrElseGet implements Sample {
    public String chapter(){return "05_Streams_Lambda_Functional";}
    public int problem(){return 65;}
    public String title(){return "Optional の orElse と orElseGet の評価タイミング";}
    private String expensive(String label){ System.out.println("  expensive(" + label + ") が呼ばれた"); return "default"; }
    public void run(){
        Optional<String> present = Optional.of("value");
        System.out.println("orElse（値が存在しても引数は評価される）:");
        String r1 = present.orElse(expensive("orElse"));
        System.out.println("  結果 = " + r1);
        System.out.println("orElseGet（値が存在すれば Supplier は呼ばれない）:");
        String r2 = present.orElseGet(() -> expensive("orElseGet"));
        System.out.println("  結果 = " + r2);
    }
}
