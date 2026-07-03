package com.x5gtrn.book.appendix.java21stw300.ch03_exception_handling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Optional;
public final class Q35NumberFormatException implements Sample {
    public String chapter(){return "03_Exception_Handling";}
    public int problem(){return 35;}
    public String title(){return "NumberFormatException のハンドリング";}
    public void run(){
        for (String in : new String[]{"42", "  7 ", "abc", ""})
            System.out.printf("parse(\"%s\") -> %s%n", in, parse(in).map(String::valueOf).orElse("(無効な数値)"));
    }
    private Optional<Integer> parse(String s){
        try { return Optional.of(Integer.parseInt(s.trim())); }
        catch (NumberFormatException e){ return Optional.empty(); }
    }
}
