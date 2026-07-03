package com.x5gtrn.book.appendix.java21stw300.ch03_exception_handling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q43ExceptionTranslationBoundary implements Sample {
    static final class SqlLikeException extends RuntimeException { SqlLikeException(String m){ super(m); } }
    // API 境界で返す構造化エラー（内部詳細やスタックを漏らさない）
    record ApiError(int status, String code, String message) {}
    public String chapter(){return "03_Exception_Handling";}
    public int problem(){return 43;}
    public String title(){return "例外変換と REST API 境界の設計";}
    public void run(){
        System.out.println("正常時 -> " + toResponse(false));
        System.out.println("異常時 -> " + toResponse(true));
    }
    private ApiError toResponse(boolean fail){
        try {
            if (fail) throw new SqlLikeException("ORA-00942: table or view does not exist");
            return new ApiError(200, "OK", "success");
        } catch (SqlLikeException e) {
            // 内部例外メッセージ（DB構造など）はログにのみ残し、外部へは一般化した情報を返す
            System.out.println("  [内部ログ] " + e.getMessage());
            return new ApiError(500, "INTERNAL_ERROR", "処理中にエラーが発生しました");
        }
    }
}
