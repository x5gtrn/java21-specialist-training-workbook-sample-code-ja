package com.x5gtrn.book.appendix.java21stw300.ch10_design_patterns;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q146SingletonVsDi implements Sample {
    // アンチパターン: 古典的シングルトン（グローバル状態・差し替え不可でテストしにくい）
    static final class GlobalClock { static final GlobalClock INSTANCE = new GlobalClock(); long now(){ return 12345L; } }
    // 推奨: 依存をインターフェースでコンストラクタ注入（DIコンテナが単一インスタンスを管理）
    interface Clock { long now(); }
    static final class Service {
        private final Clock clock;
        Service(Clock clock){ this.clock = clock; }
        long timestamp(){ return clock.now(); }
    }
    public String chapter(){return "10_Design_Patterns";}
    public int problem(){return 146;}
    public String title(){return "Singleton と DI Container";}
    public void run(){
        System.out.println("古典シングルトン: " + GlobalClock.INSTANCE.now() + "（差し替え不可・グローバル状態）");
        Service prod = new Service(System::currentTimeMillis);  // 本番は実時刻の実装を注入
        Service test = new Service(() -> 42L);                  // テストでは固定値を注入できる
        System.out.println("DI(本番=実時刻) = " + prod.timestamp() + " / DI(テスト=固定) = " + test.timestamp());
    }
}
