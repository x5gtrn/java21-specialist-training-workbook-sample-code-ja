package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Set;
public final class Q234CorsMisconfiguration implements Sample {
    static final Set<String> ALLOWLIST = Set.of("https://app.example.com");
    public String chapter(){return "16_Security_Auth";}
    public int problem(){return 234;}
    public String title(){return "CORS の誤設定リスク";}
    public void run(){
        // 危険: Allow-Origin を動的に反射 + Allow-Credentials:true は任意サイトからの認証付き要求を許す
        System.out.println("[危険] 反射的許可 : evil.example.com からの Cookie 付きリクエストが通ってしまう");
        // 安全: 明示的な allowlist と照合し、資格情報付きでは '*' を使わない
        for (String origin : new String[]{"https://app.example.com","https://evil.example.com"})
            System.out.printf("Origin=%-28s -> %s%n", origin,
                ALLOWLIST.contains(origin) ? "Access-Control-Allow-Origin を付与" : "許可しない");
        System.out.println("資格情報(credentials)使用時は Allow-Origin='*' は不可。allowlist で厳密に指定する");
    }
}
