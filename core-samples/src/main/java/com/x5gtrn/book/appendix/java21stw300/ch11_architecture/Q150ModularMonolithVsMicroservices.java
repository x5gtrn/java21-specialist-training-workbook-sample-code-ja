package com.x5gtrn.book.appendix.java21stw300.ch11_architecture;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q150ModularMonolithVsMicroservices implements Sample {
    public String chapter(){ return "11_Architecture"; }
    public int problem(){ return 150; }
    public String title(){ return "Modular Monolith と Microservices"; }
    public void run(){
        System.out.println("モジュラモノリス: 単一デプロイ + 明確なモジュール境界（プロセス内呼び出し）");
        System.out.println("  長所: 運用が単純・トランザクション/整合が容易・低レイテンシ・リファクタリングが安全");
        System.out.println("マイクロサービス: 独立デプロイ・独立スケール・技術選択の自由");
        System.out.println("  短所: 分散トランザクション/結果整合・ネットワーク障害・運用と観測の複雑化");
        System.out.println("指針: まずモジュラモノリスで境界を育て、真にスケール/組織上の必要が出たら切り出す");
        System.out.println("境界が曖昧なまま分割すると『分散モノリス』となり、両者の短所だけを抱える");
    }
}
