package com.x5gtrn.book.appendix.java21stw300.ch19_logging_observability;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
public final class Q279CorrelationId implements Sample {
    private static final ThreadLocal<String> CORRELATION_ID = new ThreadLocal<>();
    public String chapter(){ return "19_Logging_Observability"; }
    public int problem(){ return 279; }
    public String title(){ return "非同期処理と Correlation ID"; }
    public void run() throws Exception {
        CORRELATION_ID.set("req-abc-123");
        log("リクエスト受信");
        String captured = CORRELATION_ID.get(); // 別スレッドへは明示的に引き継ぐ必要がある
        try (var pool = Executors.newFixedThreadPool(2)) {
            CompletableFuture.runAsync(() -> {
                CORRELATION_ID.set(captured);   // 境界を越えて伝播（MDCは自動継承されない）
                try { log("非同期処理を実行中"); } finally { CORRELATION_ID.remove(); }
            }, pool).get();
        }
        log("レスポンス返却");
        CORRELATION_ID.remove();
        System.out.println("同一 correlationId で全ログを串刺し検索でき、分散/非同期でも一連の流れを追える");
        System.out.println("実務: フィルタで生成→MDC格納、スレッドプール/@Async にはデコレータで伝播、W3C traceparent 連携");
    }
    private void log(String msg){ System.out.println("[cid=" + CORRELATION_ID.get() + "] " + msg); }
}
