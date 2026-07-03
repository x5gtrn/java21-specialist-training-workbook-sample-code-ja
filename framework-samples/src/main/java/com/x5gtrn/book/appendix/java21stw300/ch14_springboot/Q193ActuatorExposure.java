package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
@Component
public class Q193ActuatorExposure implements FrameworkSample {
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 193; }
    public String title(){ return "Actuator エンドポイントの公開範囲"; }
    public void run(){
        // 既定では web に health のみ公開。危険な情報系を無防備に晒さないことが重要。
        System.out.println("既定: web には /health のみ公開（env/beans/heapdump 等は非公開）");
        System.out.println("設定例: management.endpoints.web.exposure.include=health,info,metrics");
        System.out.println("危険: exposure.include=* で全公開すると /env や /heapdump から秘密情報が漏れうる");
        System.out.println("対策: 公開は必要最小限 / actuator を別ポート(management.server.port)へ分離");
        System.out.println("      認証・認可(Spring Security)で保護し、機微な情報系はネットワークからも遮断する");
    }
}
