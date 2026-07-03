package com.x5gtrn.book.appendix.java21stw300.ch03_exception_handling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q40ExceptionChaining implements Sample {
    static class DataAccessException extends RuntimeException {
        DataAccessException(String m, Throwable cause){ super(m, cause); }
    }
    public String chapter(){return "03_Exception_Handling";}
    public int problem(){return 40;}
    public String title(){return "例外チェーン（Exception Chaining）";}
    public void run(){
        try {
            loadUser();
        } catch (DataAccessException e) {
            System.out.println("捕捉: " + e.getMessage());
            for (Throwable t = e; t != null; t = t.getCause())
                System.out.println("  原因の連鎖 -> " + t.getClass().getSimpleName() + ": " + t.getMessage());
        }
    }
    private void loadUser(){
        try { Integer.parseInt("not-a-number"); }
        catch (NumberFormatException e){
            // 低レベル例外を握りつぶさず cause として保持し、文脈を足して再送出
            throw new DataAccessException("ユーザーの読み込みに失敗", e);
        }
    }
}
