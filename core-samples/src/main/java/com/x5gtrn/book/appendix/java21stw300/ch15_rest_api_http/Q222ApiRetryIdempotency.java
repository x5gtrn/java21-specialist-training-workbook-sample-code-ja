package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.function.Supplier;
public final class Q222ApiRetryIdempotency implements Sample {
    public String chapter(){ return "15_REST_API_HTTP"; }
    public int problem(){ return 222; }
    public String title(){ return "API リトライと冪等性"; }
    public void run(){
        // 指数バックオフ付きリトライ。ただし『冪等な操作』にのみ安全に適用できる。
        int[] attempt = {0};
        Supplier<String> flaky = () -> {
            if (++attempt[0] < 3) throw new RuntimeException("503 一時障害");
            return "成功";
        };
        System.out.println("結果 = " + retry(flaky, 5) + "（" + attempt[0] + "回目で成功）");
        System.out.println("安全なリトライ対象: GET/PUT/DELETE(冪等) や Idempotency-Key を持つ操作");
        System.out.println("危険: 素の POST(課金/作成)を無条件リトライ → 二重実行。まず冪等化してからリトライする");
    }
    private String retry(Supplier<String> action, int maxAttempts){
        long backoff = 10;
        for (int i = 1; i <= maxAttempts; i++) {
            try { return action.get(); }
            catch (RuntimeException e) {
                if (i == maxAttempts) throw e;
                System.out.println("  試行" + i + "失敗 -> " + backoff + "ms 待機(指数バックオフ)");
                try { Thread.sleep(backoff); } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
                backoff *= 2; // 指数的に増やす（＋本来は jitter を加える）
            }
        }
        throw new IllegalStateException();
    }
}
