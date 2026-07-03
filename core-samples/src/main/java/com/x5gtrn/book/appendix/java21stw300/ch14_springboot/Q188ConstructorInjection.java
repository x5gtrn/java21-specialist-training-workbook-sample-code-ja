package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q188ConstructorInjection implements Sample {
    interface Clock { long now(); }
    // コンストラクタ注入: 依存は final で不変・必須が明確・テストで差し替え容易
    static final class ReportService {
        private final Clock clock;
        ReportService(Clock clock){ this.clock = clock; } // フィールド注入と違い不変・null不可
        String stamp(){ return "report@" + clock.now(); }
    }
    public String chapter(){return "14_SpringBoot";}
    public int problem(){return 188;}
    public String title(){return "コンストラクタインジェクションのベストプラクティス";}
    public void run(){
        ReportService prod = new ReportService(() -> 1000L);
        ReportService test = new ReportService(() -> 42L); // テストでモックを注入
        System.out.println("本番相当: " + prod.stamp());
        System.out.println("テスト  : " + test.stamp());
        System.out.println("利点: final(不変)・必須依存の明示・new でテスト容易・循環依存を検出しやすい");
    }
}
