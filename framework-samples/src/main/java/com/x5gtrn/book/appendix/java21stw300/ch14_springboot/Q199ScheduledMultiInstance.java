package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
@Component
public class Q199ScheduledMultiInstance implements FrameworkSample {
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 199; }
    public String title(){ return "@Scheduled と複数インスタンス実行"; }
    public void run(){
        // 水平スケール時、各インスタンスの @Scheduled が同時に発火し、ジョブが多重実行される
        int instances = 3;
        System.out.println(instances + " インスタンスが稼働 -> 同一 @Scheduled が " + instances + " 回同時発火（多重実行）");
        System.out.println("結果: 二重メール送信・二重課金・バッチ重複などの障害");
        System.out.println("対策: ShedLock / DBアドバイザリロック等で『同時に1つだけ実行』を保証する");
        System.out.println("または専用スケジューラ(Quartzクラスタ, k8s CronJob)へ分離する");
    }
}
