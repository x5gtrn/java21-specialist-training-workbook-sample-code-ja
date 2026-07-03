package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
public final class Q220AcceptedAsyncApi implements Sample {
    public String chapter(){return "15_REST_API_HTTP";}
    public int problem(){return 220;}
    public String title(){return "202 Accepted と非同期処理 API";}
    public void run() throws Exception {
        Map<String,String> jobs = new ConcurrentHashMap<>();
        try (var pool = Executors.newSingleThreadExecutor()) {
            // 重い処理は即時に完了させず、202 + ジョブID を返してバックグラウンド実行
            String jobId = UUID.randomUUID().toString().substring(0,8);
            jobs.put(jobId, "PROCESSING");
            System.out.println("POST /reports -> 202 Accepted, Location: /jobs/" + jobId);
            pool.submit(() -> { try { Thread.sleep(100); } catch (InterruptedException ignored){} jobs.put(jobId, "COMPLETED"); });
            // クライアントは /jobs/{id} をポーリング
            while (!"COMPLETED".equals(jobs.get(jobId))) Thread.sleep(20);
            System.out.println("GET /jobs/" + jobId + " -> 200 { status: " + jobs.get(jobId) + " }");
        }
    }
}
