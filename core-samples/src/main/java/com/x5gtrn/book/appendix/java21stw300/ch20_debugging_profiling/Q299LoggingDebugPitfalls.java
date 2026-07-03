package com.x5gtrn.book.appendix.java21stw300.ch20_debugging_profiling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q299LoggingDebugPitfalls implements Sample {
    public String chapter(){ return "20_Debugging_Profiling"; }
    public int problem(){ return 299; }
    public String title(){ return "ログを使ったデバッグの落とし穴"; }
    public void run(){
        System.out.println("落とし穴1: ログ追加で処理タイミングが変わり、競合バグが再現しなくなる(ハイゼンバグ)");
        System.out.println("落とし穴2: log してから throw する二重処理 → 同一例外が多重ログ化しノイズになる");
        System.out.println("  対策: 捕捉して処理する層で1回だけログ。再送出時はログしない（catch-log-throw を避ける）");
        System.out.println("落とし穴3: 高頻度パスでの文字列連結/重い toString がスループットを劣化させる");
        System.out.println("  対策: パラメータ化ログ log.debug(\"x={}\", v) と isDebugEnabled ガード");
        System.out.println("落とし穴4: 文脈不足のログ(『エラー発生』のみ) → correlationId/主要パラメータを含める");
        System.out.println("本質的な不具合は、ログ増設より デバッガ/条件付きブレークポイント/観測可能性 で捉える");
    }
}
