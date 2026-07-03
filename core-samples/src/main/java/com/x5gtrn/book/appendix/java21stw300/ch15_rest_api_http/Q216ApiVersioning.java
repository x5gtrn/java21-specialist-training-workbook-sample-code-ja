package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q216ApiVersioning implements Sample {
    public String chapter(){return "15_REST_API_HTTP";}
    public int problem(){return 216;}
    public String title(){return "API バージョニング戦略";}
    public void run(){
        // URL パス方式 / ヘッダ方式で v1・v2 を振り分ける（後方互換を保ちつつ進化）
        System.out.println("GET /api/v1/users -> " + route("/api/v1/users", null));
        System.out.println("GET /api/v2/users -> " + route("/api/v2/users", null));
        System.out.println("GET /api/users (Accept: v2) -> " + route("/api/users", "application/vnd.example.v2+json"));
    }
    private String route(String path, String accept){
        if (path.contains("/v2/") || "application/vnd.example.v2+json".equals(accept))
            return "UserV2{id,name,email}（項目追加版）";
        return "UserV1{id,name}";
    }
}
