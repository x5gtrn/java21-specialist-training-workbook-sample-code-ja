package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Set;
public final class Q225CorsVsAuth implements Sample {
    static final Set<String> ALLOWED_ORIGINS = Set.of("https://app.example.com");
    public String chapter(){return "15_REST_API_HTTP";}
    public int problem(){return 225;}
    public String title(){return "CORS と認証・認可の違い";}
    public void run(){
        // CORS: 「どの Origin のブラウザJSがレスポンスを読めるか」を制御（ブラウザ向けの仕組み）
        check("https://app.example.com", "Bearer valid");
        check("https://evil.example.com", "Bearer valid");
        check("https://app.example.com", null);
        System.out.println("CORS は認証/認可の代替ではない。CORS を許可しても、認証トークンの検証は別途必須");
    }
    private void check(String origin, String auth){
        boolean corsOk = ALLOWED_ORIGINS.contains(origin);        // ブラウザが読めるか
        boolean authOk = auth != null && auth.startsWith("Bearer ");// 本人確認できるか（サーバ側）
        System.out.printf("Origin=%-28s CORS=%-5s 認証=%-5s%n", origin, corsOk, authOk);
    }
}
