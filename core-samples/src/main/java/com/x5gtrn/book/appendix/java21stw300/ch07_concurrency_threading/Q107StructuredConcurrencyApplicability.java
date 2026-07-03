package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.StructuredTaskScope;
public final class Q107StructuredConcurrencyApplicability implements Sample {
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 107;}
    public String title(){return "Structured Concurrency の適用判断";}
    @Override public boolean preview(){return true;}
    public void run() throws Exception {
        // ShutdownOnSuccess: 最初に成功したサブタスクの結果を採用し、残りをキャンセル（フェイルオーバー等）
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            scope.fork(() -> { Thread.sleep(150); return "レプリカA(遅い)"; });
            scope.fork(() -> { Thread.sleep(30);  return "レプリカB(速い)"; });
            scope.join();
            System.out.println("最初に成功した結果 = " + scope.result());
        }
    }
}
