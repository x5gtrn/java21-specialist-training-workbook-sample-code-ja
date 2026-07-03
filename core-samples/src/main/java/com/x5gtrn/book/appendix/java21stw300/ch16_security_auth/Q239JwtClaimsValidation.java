package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.time.Instant;
import java.util.List;
import java.util.Map;
public final class Q239JwtClaimsValidation implements Sample {
    public String chapter(){return "16_Security_Auth";}
    public int problem(){return 239;}
    public String title(){return "JWT 検証で確認すべきクレーム";}
    public void run(){
        long now = Instant.now().getEpochSecond();
        Map<String,Object> claims = Map.of(
            "iss","https://auth.example.com", "aud","my-api",
            "exp", now + 3600, "nbf", now - 10, "sub","user-1");
        System.out.println("検証結果: " + validate(claims, now));
        // 署名検証(改ざん検知)に加え、これらのクレーム検証を怠ると別用途トークンの流用等を許す
        Map<String,Object> expired = Map.of("iss","https://auth.example.com","aud","my-api","exp", now-1,"nbf",now-100);
        System.out.println("期限切れトークン: " + validate(expired, now));
    }
    private String validate(Map<String,Object> c, long now){
        if (!"https://auth.example.com".equals(c.get("iss"))) return "NG: iss(発行者)不一致";
        if (!List.of("my-api").contains(String.valueOf(c.get("aud")))) return "NG: aud(受信者)不一致";
        if (now >= ((Number)c.get("exp")).longValue()) return "NG: exp(期限切れ)";
        if (now < ((Number)c.get("nbf")).longValue()) return "NG: nbf(有効化前)";
        return "OK: iss/aud/exp/nbf すべて妥当";
    }
}
