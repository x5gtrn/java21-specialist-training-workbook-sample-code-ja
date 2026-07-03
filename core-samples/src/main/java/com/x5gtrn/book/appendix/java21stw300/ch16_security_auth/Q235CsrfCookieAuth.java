package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q235CsrfCookieAuth implements Sample {
    public String chapter(){ return "16_Security_Auth"; }
    public int problem(){ return 235; }
    public String title(){ return "CSRF 対策と Cookie ベース認証"; }
    public void run(){
        // ブラウザは対象サイトの Cookie を自動送信する → 攻撃サイトからのリクエストにも認証が付く
        System.out.println("攻撃: 悪意あるサイトが <form action=bank.com/transfer> を自動送信");
        System.out.println("     被害者のブラウザは bank.com の Cookie を自動付与 → 本人の操作として実行される");
        System.out.println("対策1: CSRF トークン（サーバ発行の秘密値をフォーム/ヘッダに要求し照合）");
        System.out.println("対策2: SameSite=Lax/Strict Cookie（クロスサイト送信を抑止）");
        System.out.println("対策3: 状態変更は GET でなく POST/PUT/DELETE に限定");
        System.out.println("補足: Cookie でなく Authorization ヘッダ(トークン)方式は自動送信されないため CSRF に強い");
    }
}
