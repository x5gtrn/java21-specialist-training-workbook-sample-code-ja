package com.x5gtrn.book.appendix.java21stw300.ch03_exception_handling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q42InterruptedExceptionHandling implements Sample {
    public String chapter(){ return "03_Exception_Handling"; }
    public int problem(){ return 42; }
    public String title(){ return "InterruptedException の正しい扱い"; }
    public void run() throws InterruptedException {
        Thread worker = new Thread(() -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                // 握りつぶさず割り込み状態を復元する（上位が割り込みを検知できるように）
                Thread.currentThread().interrupt();
                System.out.println("  割り込みを検知 -> interrupt状態を復元して終了。isInterrupted="
                        + Thread.currentThread().isInterrupted());
            }
        });
        worker.start();
        Thread.sleep(50);
        worker.interrupt(); // 割り込みを要求
        worker.join();
        System.out.println("誤り: catch で何もしない/状態を復元しないと、割り込みが失われ停止できなくなる");
        System.out.println("正: interrupt() で状態復元、または InterruptedException を上位へ再スロー");
    }
}
