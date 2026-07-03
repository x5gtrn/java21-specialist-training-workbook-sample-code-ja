package com.x5gtrn.book.appendix.java21stw300.ch11_architecture;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.Map;
public final class Q160ArchitectureFitnessFunction implements Sample {
    public String chapter(){return "11_Architecture";}
    public int problem(){return 160;}
    public String title(){return "Architecture Fitness Functions";}
    public void run(){
        // 依存関係の一覧（本来は ArchUnit 等でクラスパスを走査して検査する）
        Map<String,List<String>> deps = Map.of(
            "domain.Order", List.of("domain.Money"),
            "app.OrderService", List.of("domain.Order", "domain.Money"),
            "infra.OrderRepository", List.of("app.OrderService", "domain.Order"));
        // 適応度関数: 「domain は infra/app に依存してはならない」を自動検証
        boolean ok = true;
        for (var e : deps.entrySet()){
            if (e.getKey().startsWith("domain.")){
                for (String d : e.getValue())
                    if (d.startsWith("infra.") || d.startsWith("app.")){
                        System.out.println("違反: " + e.getKey() + " -> " + d); ok = false;
                    }
            }
        }
        System.out.println("アーキテクチャ適応度関数の検査結果: " + (ok ? "PASS（依存方向は健全）" : "FAIL"));
        System.out.println("この検査を CI に組み込むと、設計ルールの逸脱を継続的に検出できる");
    }
}
