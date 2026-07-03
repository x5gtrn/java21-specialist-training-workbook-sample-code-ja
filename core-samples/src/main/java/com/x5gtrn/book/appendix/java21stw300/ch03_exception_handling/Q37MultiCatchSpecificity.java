package com.x5gtrn.book.appendix.java21stw300.ch03_exception_handling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q37MultiCatchSpecificity implements Sample {
    public String chapter(){ return "03_Exception_Handling"; }
    public int problem(){ return 37; }
    public String title(){ return "マルチキャッチと例外型の特定性"; }
    public void run(){
        for (String in : List.of("num", "npe")) {
            try {
                if (in.equals("num")) Integer.parseInt("x");
                else { String s = null; s.length(); }
            } catch (NumberFormatException | NullPointerException e) { // 兄弟型をまとめて捕捉
                System.out.println("マルチキャッチ -> " + e.getClass().getSimpleName());
            }
        }
        System.out.println("マルチキャッチの型同士は継承関係にできない（重複禁止）。変数 e は実質 final");
    }
}
