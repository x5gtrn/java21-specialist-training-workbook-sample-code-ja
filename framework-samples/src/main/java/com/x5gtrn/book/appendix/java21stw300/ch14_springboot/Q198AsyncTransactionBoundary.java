package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
@Component
public class Q198AsyncTransactionBoundary implements FrameworkSample {
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 198; }
    public String title(){ return "@Async と @Transactional の境界"; }
    public void run() throws Exception {
        long caller = Thread.currentThread().threadId();
        // @Async は別スレッドで実行される → 呼び出し元のトランザクション/永続化コンテキストは引き継がれない
        Thread worker = new Thread(() ->
            System.out.println("  @Async 相当のスレッド id=" + Thread.currentThread().threadId()
                + "（呼び出し元Tx・EntityManager は共有されない）"));
        worker.start(); worker.join();
        System.out.println("呼び出し元スレッド id=" + caller);
        System.out.println("注意: @Transactional メソッド内で LAZY 取得したエンティティを @Async に渡すと");
        System.out.println("      別スレッド/別コンテキストとなり LazyInitializationException を招く");
        System.out.println("対策: @Async 側で新規Txを張る / 必要データを事前にDTO化して渡す");
    }
}
