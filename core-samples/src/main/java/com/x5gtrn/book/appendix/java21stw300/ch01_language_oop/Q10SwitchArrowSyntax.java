package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q10SwitchArrowSyntax implements Sample {
    enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }
    public String chapter(){ return "01_Language_OOP"; }
    public int problem(){ return 10; }
    public String title(){ return "switch 式のアロー構文"; }
    public void run(){
        for (Day d : Day.values()) System.out.println(d + " -> " + kind(d));
    }
    private String kind(Day d){
        // アロー構文はフォールスルー無し・値を返す式。全列挙を網羅すれば default 不要（網羅性検査）。
        return switch (d) {
            case SAT, SUN -> "週末";
            case MON, TUE, WED, THU, FRI -> "平日";
        };
    }
}
