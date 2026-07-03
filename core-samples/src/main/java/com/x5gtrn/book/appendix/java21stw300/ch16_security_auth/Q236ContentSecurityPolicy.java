package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q236ContentSecurityPolicy implements Sample {
    public String chapter(){ return "16_Security_Auth"; }
    public int problem(){ return 236; }
    public String title(){ return "Content Security Policy と XSS 低減"; }
    public void run(){
        String userInput = "<script>steal(document.cookie)</script>";
        // 出力エスケープ: HTML 特殊文字を実体参照へ変換し、スクリプトとして実行させない
        System.out.println("入力      : " + userInput);
        System.out.println("エスケープ後: " + htmlEscape(userInput));
        System.out.println("CSP ヘッダ例: Content-Security-Policy: default-src 'self'; script-src 'self'");
        System.out.println("  -> インラインスクリプトや外部由来スクリプトの実行を既定で禁止（XSSの被害を大幅低減）");
        System.out.println("多層防御: 出力エスケープ(第一防御) + CSP(第二防御)。CSP だけ/エスケープだけに頼らない");
    }
    private String htmlEscape(String s){
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;")
                .replace("\"","&quot;").replace("'","&#x27;");
    }
}
