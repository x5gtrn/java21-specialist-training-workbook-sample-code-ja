package com.x5gtrn.book.appendix.java21stw300.ch11_architecture;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
public final class Q153EventDrivenBasics implements Sample {
    // 疎結合な発行/購読。発行側は購読側を知らない。
    static final class EventBus {
        private final List<Consumer<String>> subscribers = new ArrayList<>();
        void subscribe(Consumer<String> s){ subscribers.add(s); }
        void publish(String event){ subscribers.forEach(s -> s.accept(event)); }
    }
    public String chapter(){return "11_Architecture";}
    public int problem(){return 153;}
    public String title(){return "イベント駆動アーキテクチャの基礎";}
    public void run(){
        EventBus bus = new EventBus();
        bus.subscribe(e -> System.out.println("  在庫サービス: " + e + " を受信 → 引当"));
        bus.subscribe(e -> System.out.println("  通知サービス: " + e + " を受信 → メール送信"));
        bus.publish("OrderPlaced(A-1)");
        System.out.println("発行側は購読側の存在・数を知らない（疎結合・拡張容易）");
    }
}
