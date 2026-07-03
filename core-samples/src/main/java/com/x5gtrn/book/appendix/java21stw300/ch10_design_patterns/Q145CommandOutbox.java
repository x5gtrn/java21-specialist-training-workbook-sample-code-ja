package com.x5gtrn.book.appendix.java21stw300.ch10_design_patterns;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayList;
import java.util.List;
public final class Q145CommandOutbox implements Sample {
    // 状態変更と「送信すべきイベント」を同一トランザクションで outbox に記録し、後で確実に配信する
    record OrderCreated(String orderId) {}
    static final class OrderService {
        final List<OrderCreated> outbox = new ArrayList<>();
        void createOrder(String id){ /* DB 保存 */ outbox.add(new OrderCreated(id)); }
    }
    public String chapter(){return "10_Design_Patterns";}
    public int problem(){return 145;}
    public String title(){return "Command パターンと Outbox";}
    public void run(){
        OrderService svc = new OrderService();
        svc.createOrder("A-1"); svc.createOrder("A-2");
        System.out.println("outbox に記録されたイベント = " + svc.outbox);
        // 別プロセス/バッチが outbox をポーリングして発行 → DB更新とイベント発行の原子性を担保
        svc.outbox.forEach(e -> System.out.println("  publish -> " + e));
        svc.outbox.clear();
    }
}
