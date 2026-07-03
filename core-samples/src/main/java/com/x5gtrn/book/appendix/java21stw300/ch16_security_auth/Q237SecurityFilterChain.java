package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.function.Function;
public final class Q237SecurityFilterChain implements Sample {
    record Request(String path, String token, String role) {}
    public String chapter(){return "16_Security_Auth";}
    public int problem(){return 237;}
    public String title(){return "Spring Security FilterChain";}
    public void run(){
        // フィルタチェーン: 各フィルタが順に判定し、拒否なら以降を実行しない（概念モデル）
        List<Function<Request,String>> chain = List.of(
            req -> req.path().startsWith("/public") ? "PERMIT(public)" : null,          // 公開パス
            req -> req.token() == null ? "DENY(401 未認証)" : null,                       // 認証
            req -> "/admin".equals(req.path()) && !"ADMIN".equals(req.role()) ? "DENY(403 権限不足)" : null // 認可
        );
        for (Request r : List.of(new Request("/public/info", null, null),
                                 new Request("/admin", null, "USER"),
                                 new Request("/admin", "tok", "USER"),
                                 new Request("/admin", "tok", "ADMIN")))
            System.out.println(r + " -> " + evaluate(chain, r));
    }
    private String evaluate(List<Function<Request,String>> chain, Request r){
        for (var f : chain){ String d = f.apply(r); if (d != null) return d; }
        return "PERMIT(認証・認可OK)";
    }
}
