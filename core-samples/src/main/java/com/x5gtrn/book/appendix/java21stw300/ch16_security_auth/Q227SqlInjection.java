package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q227SqlInjection implements Sample {
    public String chapter(){ return "16_Security_Auth"; }
    public int problem(){ return 227; }
    public String title(){ return "SQL インジェクション対策"; }
    public void run(){
        String input = "' OR '1'='1";                       // 認証回避を狙う入力
        // 危険: 文字列連結でクエリを組み立てると、入力が構文として解釈される
        String unsafe = "SELECT * FROM users WHERE name = '" + input + "'";
        System.out.println("危険(連結): " + unsafe);
        System.out.println("  -> WHERE 句が常に真になり全件漏洩や認証回避を許す");
        // 安全: プレースホルダ + バインド。値はデータとして扱われ、構文には混ざらない
        System.out.println("安全(プレースホルダ): SELECT * FROM users WHERE name = ?");
        System.out.println("  -> PreparedStatement.setString(1, input) で入力はデータ扱い（エスケープ不要）");
        System.out.println("補足: 動的な列名/並び順は値バインド不可 → 許可リスト照合で対処する");
    }
}
