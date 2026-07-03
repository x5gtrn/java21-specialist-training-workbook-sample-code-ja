package com.x5gtrn.book.appendix.java21stw300.ch10_design_patterns;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.function.Function;
public final class Q140TemplateMethodVsStrategy implements Sample {
    // Template Method: アルゴリズムの骨格を固定し、一部をサブクラスで差し替え
    static abstract class ReportProcessor {
        final String process(String data){ return "[" + header() + "] " + transform(data); }
        abstract String header();
        abstract String transform(String data);
    }
    static final class UpperReport extends ReportProcessor {
        String header(){ return "UPPER"; }
        String transform(String d){ return d.toUpperCase(); }
    }
    public String chapter(){return "10_Design_Patterns";}
    public int problem(){return 140;}
    public String title(){return "Template Method と Strategy の使い分け";}
    public void run(){
        System.out.println("Template Method: " + new UpperReport().process("hello"));
        // Strategy: 振る舞いを関数として注入（実行時に差し替え可能）
        Function<String, String> reverse = s -> new StringBuilder(s).reverse().toString();
        System.out.println("Strategy:        " + reverse.apply("hello"));
    }
}
