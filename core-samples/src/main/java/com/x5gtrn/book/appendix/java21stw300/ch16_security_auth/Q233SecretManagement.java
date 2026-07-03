package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q233SecretManagement implements Sample {
    public String chapter(){ return "16_Security_Auth"; }
    public int problem(){ return 233; }
    public String title(){ return "シークレット管理と設定ファイル"; }
    public void run(){
        // 危険: ソース/リポジトリに秘密情報を直書き（履歴に残り続け、漏洩時の影響大）
        System.out.println("危険例: String apiKey = \"sk_live_ABCDEF...\";  // ハードコード厳禁");
        // 推奨: 環境変数/シークレットマネージャから注入
        String fromEnv = System.getenv("DB_PASSWORD");
        System.out.println("環境変数 DB_PASSWORD の取得 = " + (fromEnv == null ? "(未設定)" : "***取得***"));
        System.out.println("推奨: 環境変数 / Vault / AWS Secrets Manager / K8s Secret から実行時注入");
        System.out.println("設定ファイルにはプレースホルダのみ置き、実値はリポジトリに含めない(.gitignore)");
        System.out.println("万一コミットした場合は履歴から除去し、必ずローテーション(鍵の再発行)する");
    }
}
