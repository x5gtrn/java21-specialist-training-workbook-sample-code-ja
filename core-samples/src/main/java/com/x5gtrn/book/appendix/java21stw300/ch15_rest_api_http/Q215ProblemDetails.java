package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q215ProblemDetails implements Sample {
    // RFC 7807: application/problem+json の標準フィールド（type/title/status/detail/instance）
    record ProblemDetail(String type, String title, int status, String detail, String instance) {
        String toJson(){
            return String.format("{\"type\":\"%s\",\"title\":\"%s\",\"status\":%d,\"detail\":\"%s\",\"instance\":\"%s\"}",
                    type, title, status, detail, instance);
        }
    }
    public String chapter(){return "15_REST_API_HTTP";}
    public int problem(){return 215;}
    public String title(){return "RFC 7807 Problem Details";}
    public void run(){
        ProblemDetail pd = new ProblemDetail(
            "https://example.com/problems/insufficient-funds",
            "口座残高が不足しています", 409,
            "残高 30 に対して 50 の出金が要求されました", "/accounts/12345/withdraw");
        System.out.println("Content-Type: application/problem+json");
        System.out.println(pd.toJson());
        System.out.println("標準化された機械可読エラー形式により、クライアントが一貫して処理できる");
    }
}
