package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
@Component
public class Q192Actuator implements FrameworkSample {
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 192; }
    public String title(){ return "Spring Boot Actuator"; }
    public void run(){
        // 注: 本サンプルは actuator 非依存のため概念を提示（実際は spring-boot-starter-actuator を追加）
        System.out.println("Actuator は運用向けエンドポイントを提供する（本番の観測性の土台）:");
        System.out.println("  /actuator/health  : 稼働状態(DB/ディスク等の集約)。K8s の liveness/readiness に利用");
        System.out.println("  /actuator/metrics : Micrometer 経由の各種メトリクス");
        System.out.println("  /actuator/info    : ビルド/バージョン情報");
        System.out.println("  /actuator/loggers : 実行時のログレベル変更");
        System.out.println("health は liveness(生存)と readiness(受付可否)のグループに分け、依存の準備完了を反映させる");
    }
}
