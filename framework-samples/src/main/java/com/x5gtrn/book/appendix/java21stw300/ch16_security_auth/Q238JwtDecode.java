package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;

import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 問題238: OAuth2/JWT。
 * JWT は header.payload.signature の 3 部構成で、各部は Base64URL。
 * デコード（中身を読む）と署名検証（鍵が必要）は別物である点に注意。
 */
@Component
public class Q238JwtDecode implements FrameworkSample {

    public String chapter() { return "16_Security_Auth"; }
    public int problem()    { return 238; }
    public String title()   { return "JWT の構造とデコード"; }

    public void run() {
        // デモ用に JSON から JWT 文字列を組み立てる
        String headerJson = "{\"alg\":\"RS256\",\"typ\":\"JWT\"}";
        String payloadJson = "{\"sub\":\"user123\",\"iss\":\"auth.example.com\",\"exp\":1706097600,\"roles\":[\"USER\"]}";
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        String token = enc.encodeToString(headerJson.getBytes(StandardCharsets.UTF_8))
                + "." + enc.encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8))
                + ".S1gNaturePlaceholder";

        System.out.println("JWT: " + token.substring(0, Math.min(48, token.length())) + " ...");

        String[] parts = token.split("\\.");
        Base64.Decoder dec = Base64.getUrlDecoder();
        System.out.println("header  = " + new String(dec.decode(parts[0]), StandardCharsets.UTF_8));
        System.out.println("payload = " + new String(dec.decode(parts[1]), StandardCharsets.UTF_8));
        System.out.println("signature(先頭) = " + parts[2].substring(0, Math.min(10, parts[2].length())) + "...");
        System.out.println("注: デコードは誰でも可能。改ざん検知には署名検証（公開鍵）が必須。");
    }
}
