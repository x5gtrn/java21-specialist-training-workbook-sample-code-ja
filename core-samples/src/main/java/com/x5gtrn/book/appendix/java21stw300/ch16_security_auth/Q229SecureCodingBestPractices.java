package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q229SecureCodingBestPractices implements Sample {
    public String chapter(){ return "16_Security_Auth"; }
    public int problem(){ return 229; }
    public String title(){ return "セキュアコーディングのベストプラクティス"; }
    public void run(){
        System.out.println("1. 入力を信頼しない: 境界で検証（許可リスト方式）し、出力時は文脈に応じてエスケープ");
        System.out.println("2. 最小権限: DBユーザ/ファイル/トークンのスコープを必要最小限に");
        System.out.println("3. 安全側に倒す(Fail securely): 例外時はデフォルト拒否、内部情報を漏らさない");
        System.out.println("4. 多層防御(Defense in depth): 単一対策に依存せず層を重ねる");
        System.out.println("5. 既製の実績あるライブラリを使う（暗号/認証を自作しない）");
        System.out.println("6. 秘密情報をログ/例外/URLに出さない、依存の脆弱性を継続的に監査(SCA)");
        System.out.println("原則: セキュリティは後付けでなく設計時から（Secure by Design）");
    }
}
