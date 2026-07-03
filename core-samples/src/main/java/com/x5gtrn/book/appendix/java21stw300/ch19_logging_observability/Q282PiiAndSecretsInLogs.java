package com.x5gtrn.book.appendix.java21stw300.ch19_logging_observability;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q282PiiAndSecretsInLogs implements Sample {
    public String chapter(){ return "19_Logging_Observability"; }
    public int problem(){ return 282; }
    public String title(){ return "ログにおける PII と Secret の扱い"; }
    public void run(){
        System.out.println("メール    : " + maskEmail("alice.smith@example.com"));
        System.out.println("カード番号: " + maskCard("4111111111111111"));
        System.out.println("トークン  : " + maskSecret("Bearer sk_live_ABCDEF123456"));
        System.out.println("原則: PII(個人情報)や秘密情報はログ出力前にマスキング/除外する");
        System.out.println("危険例: リクエスト/レスポンス全体のダンプ、例外に載る入力値、Authorizationヘッダ");
        System.out.println("対策: DTOに@ToString.Exclude相当・専用マスカー・ログ収集段でのredaction。法規制(GDPR等)にも直結");
    }
    private String maskEmail(String e){ int at = e.indexOf('@');
        return at <= 1 ? "***" : e.charAt(0) + "***" + e.substring(at); }
    private String maskCard(String c){ return "****-****-****-" + c.substring(c.length()-4); }
    private String maskSecret(String s){ return s.replaceAll("(?i)(bearer\\s+\\S{4})\\S+", "$1****"); }
}
