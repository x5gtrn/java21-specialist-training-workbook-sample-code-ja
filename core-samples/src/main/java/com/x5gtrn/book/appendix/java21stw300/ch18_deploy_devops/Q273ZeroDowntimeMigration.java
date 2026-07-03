package com.x5gtrn.book.appendix.java21stw300.ch18_deploy_devops;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q273ZeroDowntimeMigration implements Sample {
    public String chapter(){ return "18_Deploy_DevOps"; }
    public int problem(){ return 273; }
    public String title(){ return "ゼロダウンタイムデータベースマイグレーション"; }
    public void run(){
        // Expand-Contract(拡張→移行→縮小): 新旧コードが同時に動ける後方互換な段階変更
        System.out.println("列名変更を無停止で行う手順（Expand and Contract）:");
        System.out.println("  1. Expand : 新カラムを追加（NULL許容）。旧カラムは残す（旧コードと互換）");
        System.out.println("  2. Migrate: アプリを両方書き込みに更新 → 既存データをバックフィル");
        System.out.println("  3. Switch : 読み取りを新カラムへ切替（全インスタンス入替後）");
        System.out.println("  4. Contract: 旧カラムへの依存を除去し、最後に旧カラムを削除");
        System.out.println("原則: 各ステップは後方互換。ローリングデプロイ中に新旧が共存できることが必須");
        System.out.println("破壊的変更(rename/drop/not null 追加)を1回で行うとデプロイ中に障害が出る");
    }
}
