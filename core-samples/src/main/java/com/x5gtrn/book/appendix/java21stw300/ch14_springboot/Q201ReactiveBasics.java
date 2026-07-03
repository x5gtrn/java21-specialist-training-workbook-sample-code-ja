package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.concurrent.CompletableFuture;
public final class Q201ReactiveBasics implements Sample {
    public String chapter(){return "14_SpringBoot";}
    public int problem(){return 201;}
    public String title(){return "Spring WebFlux の基礎";}
    public void run() throws Exception {
        // WebFlux/Reactor はノンブロッキングで処理を合成する。ここでは CompletableFuture で同等の考え方を示す。
        CompletableFuture<Integer> pipeline =
            CompletableFuture.supplyAsync(() -> 10)          // 非同期ソース
                .thenApply(x -> x * 2)                        // map 相当
                .thenCompose(x -> CompletableFuture.supplyAsync(() -> x + 5)); // flatMap 相当
        System.out.println("ノンブロッキング合成の結果 = " + pipeline.get());
        System.out.println("WebFlux は少数スレッドで高い並行性を出せるが、全経路がノンブロッキングである必要がある");
        System.out.println("（Reactor の Mono/Flux。ブロッキング呼び出しが混ざるとイベントループを止める）");
    }
}
