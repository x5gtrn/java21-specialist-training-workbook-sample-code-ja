package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q240StatelessSession implements Sample {
    public String chapter(){ return "16_Security_Auth"; }
    public int problem(){ return 240; }
    public String title(){ return "ステートレスセッション管理"; }
    public void run(){
        System.out.println("サーバ側セッション: セッションIDをCookieで持ち、状態はサーバに保存");
        System.out.println("  長所: 即時失効が容易 / 短所: スケール時にセッション共有(スティッキー/Redis)が必要");
        System.out.println("ステートレス(JWT): 署名付きトークンに情報を持たせ、サーバは状態を保持しない");
        System.out.println("  長所: 水平スケールが容易・サーバ間で共有不要");
        System.out.println("  短所: 即時失効が難しい(有効期限まで有効)・トークン肥大・秘密鍵漏洩の影響大");
        System.out.println("実務: アクセストークンは短命 + リフレッシュトークン、失効はブラックリスト/短TTLで対処");
    }
}
