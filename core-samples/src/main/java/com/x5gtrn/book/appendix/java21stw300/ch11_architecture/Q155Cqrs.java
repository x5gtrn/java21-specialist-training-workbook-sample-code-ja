package com.x5gtrn.book.appendix.java21stw300.ch11_architecture;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.LinkedHashMap;
import java.util.Map;
public final class Q155Cqrs implements Sample {
    // 書き込み(Command)と読み取り(Query)でモデル/経路を分離する
    static final class WriteModel {
        private final Map<String,Integer> stock = new LinkedHashMap<>();
        void handle(String product, int delta){ stock.merge(product, delta, Integer::sum); } // Command
        Map<String,Integer> raw(){ return stock; }
    }
    record StockView(String product, int quantity, String status) {} // 読み取り最適化された射影
    public String chapter(){return "11_Architecture";}
    public int problem(){return 155;}
    public String title(){return "CQRS（Command Query Responsibility Segregation）";}
    public void run(){
        WriteModel write = new WriteModel();
        write.handle("Widget", 10); write.handle("Widget", -3); // コマンド側
        // クエリ側は表示に最適化した別モデルへ射影
        write.raw().forEach((p,q) -> {
            StockView v = new StockView(p, q, q > 0 ? "在庫あり" : "欠品");
            System.out.println("Query -> " + v);
        });
        System.out.println("書き込みと読み取りを分離し、それぞれを独立に最適化/スケールできる");
    }
}
