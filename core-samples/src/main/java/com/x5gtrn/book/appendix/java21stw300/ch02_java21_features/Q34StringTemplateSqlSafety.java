package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q34StringTemplateSqlSafety implements Sample {
    public String chapter(){ return "02_Java21_Features"; }
    public int problem(){ return 34; }
    public String title(){ return "文字列テンプレートと SQL 安全性"; }
    public boolean preview(){ return true; } // 文字列テンプレートは Java 21 では Preview
    public void run(){
        String userName = "alice'; DROP TABLE users; --"; // 悪意ある入力
        // STR プロセッサは「補間」するだけでエスケープはしない → SQL に直接埋めると危険
        String unsafe = STR."SELECT * FROM users WHERE name = '\{userName}'";
        System.out.println("危険(STR直挿し): " + unsafe);
        System.out.println("  -> 補間は文字列連結と同じ穴を持つ。SQLには使わない");
        // 安全策: プレースホルダ + PreparedStatement（値はドライバがバインドしエスケープ）
        String safe = "SELECT * FROM users WHERE name = ?  -- bind: [" + userName + "]";
        System.out.println("安全(パラメータ化): " + safe);
        System.out.println("文字列テンプレートは可読な補間用途向け。値のSQL/HTML埋め込みは専用のサニタイズ機構を使う");
    }
}
