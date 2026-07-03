package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q05InterfacePrivateMethod implements Sample {
    interface Validator {
        boolean isValid(String s);
        // default メソッドが共通ロジックを private メソッドに切り出す（Java 9+）
        default String validate(String s){
            String v = normalize(s);
            return isValid(v) ? "OK: " + v : "NG: " + v;
        }
        private String normalize(String s){ return s == null ? "" : s.trim().toLowerCase(); }
    }
    public String chapter(){return "01_Language_OOP";}
    public int problem(){return 5;}
    public String title(){return "インターフェースの private メソッド";}
    public void run(){
        Validator nonBlank = s -> !s.isEmpty();
        for (String in : new String[]{"  Hello ", "   "})
            System.out.println("\"" + in + "\" -> " + nonBlank.validate(in));
    }
}
