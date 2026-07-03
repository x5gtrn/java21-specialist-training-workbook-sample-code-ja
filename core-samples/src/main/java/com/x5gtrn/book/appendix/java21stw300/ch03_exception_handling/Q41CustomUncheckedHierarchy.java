package com.x5gtrn.book.appendix.java21stw300.ch03_exception_handling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q41CustomUncheckedHierarchy implements Sample {
    // 業務例外の基底（非チェック）。共通の基底で一括捕捉でき、種類は具象で判別。
    static abstract class BusinessException extends RuntimeException {
        BusinessException(String m){ super(m); }
    }
    static final class NotFoundException extends BusinessException { NotFoundException(String m){ super(m); } }
    static final class ValidationException extends BusinessException { ValidationException(String m){ super(m); } }
    public String chapter(){return "03_Exception_Handling";}
    public int problem(){return 41;}
    public String title(){return "カスタム非チェック例外階層の設計";}
    public void run(){
        for (String op : List.of("missing", "invalid", "ok")) {
            try { handle(op); }
            catch (BusinessException e) { // 基底で受けて種類ごとに扱える
                System.out.println(e.getClass().getSimpleName() + " -> " + e.getMessage());
            }
        }
    }
    private void handle(String op){
        switch (op) {
            case "missing" -> throw new NotFoundException("対象が見つかりません");
            case "invalid" -> throw new ValidationException("入力が不正です");
            default -> System.out.println("正常処理");
        }
    }
}
