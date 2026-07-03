package com.x5gtrn.book.appendix.java21stw300.ch17_testing;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q262FlakyTestManagement implements Sample {
    public String chapter(){ return "17_Testing"; }
    public int problem(){ return 262; }
    public String title(){ return "Flaky Test の管理"; }
    public void run(){
        System.out.println("Flaky = 同じコードで成功/失敗が非決定的に変わるテスト（信頼を損ない CI を形骸化させる）");
        System.out.println("主因:");
        System.out.println("  ・時間依存(sleep固定/現在時刻) → Clock注入・Awaitilityでポーリング待ち");
        System.out.println("  ・テスト順序/共有状態 → 各テストを独立化(隔離・毎回初期化)");
        System.out.println("  ・並行性/競合 → 決定的な同期・待機に置換");
        System.out.println("  ・乱数/外部依存(ネット) → シード固定・モック/スタブ化");
        System.out.println("運用: 検知したら隔離(quarantine)しつつ根本原因を修正。安易な自動リトライは腐敗を隠す");
    }
}
