package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public final class Q221IdempotencyKey implements Sample {
    private final Map<String,String> processed = new ConcurrentHashMap<>();
    private int charges = 0;
    public String chapter(){return "15_REST_API_HTTP";}
    public int problem(){return 221;}
    public String title(){return "REST 決済 API の冪等性";}
    public void run(){
        // 同じ Idempotency-Key の再送はキャッシュ結果を返し、二重課金を防ぐ
        System.out.println("1回目(key=abc) -> " + pay("abc", 1000));
        System.out.println("再送 (key=abc) -> " + pay("abc", 1000) + "（同一キー: 再課金しない）");
        System.out.println("別リクエスト(key=xyz) -> " + pay("xyz", 500));
        System.out.println("実際の課金回数 = " + charges + "（キー重複分は課金されない）");
    }
    private String pay(String key, int amount){
        return processed.computeIfAbsent(key, k -> { charges++; return "charged:" + amount + " txn:" + (charges); });
    }
}
