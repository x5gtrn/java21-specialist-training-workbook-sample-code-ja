package com.x5gtrn.book.appendix.java21stw300.ch11_architecture;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayDeque;
import java.util.Deque;
public final class Q156Saga implements Sample {
    public String chapter(){ return "11_Architecture"; }
    public int problem(){ return 156; }
    public String title(){ return "Saga パターン"; }
    public void run(){
        // 分散トランザクションの代替。各ステップ成功を積み、失敗時は逆順に補償(ロールバック相当)する。
        System.out.println("--- 途中で失敗するケース ---");
        runSaga(true);
        System.out.println("--- 全ステップ成功するケース ---");
        runSaga(false);
    }
    private void runSaga(boolean failAtPayment){
        Deque<Runnable> compensations = new ArrayDeque<>();
        try {
            step("在庫引当", () -> System.out.println("  在庫を解放(補償)"), compensations);
            if (failAtPayment) throw new RuntimeException("決済失敗");
            step("決済", () -> System.out.println("  返金(補償)"), compensations);
            step("配送手配", () -> System.out.println("  配送キャンセル(補償)"), compensations);
            System.out.println("Saga 完了: コミット");
        } catch (RuntimeException e) {
            System.out.println("失敗(" + e.getMessage() + ") -> 補償を逆順に実行:");
            while (!compensations.isEmpty()) compensations.pop().run();
        }
    }
    private void step(String name, Runnable compensation, Deque<Runnable> comps){
        System.out.println("  実行: " + name); comps.push(compensation);
    }
}
