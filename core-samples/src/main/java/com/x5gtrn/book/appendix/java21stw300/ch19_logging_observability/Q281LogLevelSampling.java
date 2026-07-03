package com.x5gtrn.book.appendix.java21stw300.ch19_logging_observability;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q281LogLevelSampling implements Sample {
    enum Level { ERROR, WARN, INFO, DEBUG, TRACE }
    public String chapter(){ return "19_Logging_Observability"; }
    public int problem(){ return 281; }
    public String title(){ return "ログレベルとサンプリング"; }
    public void run(){
        Level threshold = Level.INFO; // これより詳細(DEBUG/TRACE)は出力しない
        for (Level l : Level.values())
            System.out.println("  " + l + " -> " + (l.ordinal() <= threshold.ordinal() ? "出力" : "抑制"));
        System.out.println("大量イベントはサンプリングで間引く（例: 1/100 のみ出力）:");
        int emitted = 0;
        for (int i = 0; i < 1000; i++) if (i % 100 == 0) emitted++;   // 1% サンプリング
        System.out.println("  1000件中 " + emitted + " 件を記録（コストと情報量のバランス）");
        System.out.println("指針: 本番は INFO 基準、障害時に DEBUG へ動的変更。高頻度ログはサンプリング/集約でコスト抑制");
    }
}
