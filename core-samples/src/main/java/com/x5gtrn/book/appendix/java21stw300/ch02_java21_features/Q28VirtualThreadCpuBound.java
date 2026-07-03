package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
public final class Q28VirtualThreadCpuBound implements Sample {
    public String chapter(){ return "02_Java21_Features"; }
    public int problem(){ return 28; }
    public String title(){ return "仮想スレッドと CPU バウンド処理"; }
    public void run() throws Exception {
        // 仮想スレッドが効くのは「I/O待ちで土台スレッドを手放せる」処理。CPUバウンドでは手放す機会が無い。
        int tasks = Runtime.getRuntime().availableProcessors() * 2;
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var results = IntStream.range(0, tasks)
                .mapToObj(i -> executor.submit(() -> heavyCompute()))
                .toList();
            long sum = 0; for (var f : results) sum += f.get();
            System.out.println("CPUバウンドタスク " + tasks + " 件を実行（結果チェックサム=" + sum + "）");
        }
        System.out.println("CPUバウンドでは仮想スレッドを増やしても並列度はコア数で頭打ち（コンテキスト切替が増えるだけ）");
        System.out.println("使い分け: I/Oバウンド→仮想スレッド有効 / CPUバウンド→コア数相当の固定プールが適切");
    }
    private long heavyCompute(){
        long acc = 0; for (int i = 0; i < 2_000_000; i++) acc += (i * 31L) ^ (acc >> 3);
        return acc;
    }
}
