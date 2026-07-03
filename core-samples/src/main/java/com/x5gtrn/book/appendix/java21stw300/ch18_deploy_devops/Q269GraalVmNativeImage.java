package com.x5gtrn.book.appendix.java21stw300.ch18_deploy_devops;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q269GraalVmNativeImage implements Sample {
    public String chapter(){ return "18_Deploy_DevOps"; }
    public int problem(){ return 269; }
    public String title(){ return "GraalVM Native Image"; }
    public void run(){
        System.out.println("Native Image: AOT コンパイルで単一ネイティブ実行ファイルを生成（JVM不要）");
        System.out.println("利点: 起動が数十ミリ秒・メモリ使用が小 → サーバレス/CLI/スケールtoゼロに好適");
        System.out.println("制約: 閉世界仮定のため、リフレクション/動的プロキシ/JNI/リソースは事前設定が必要");
        System.out.println("  -> reachability-metadata(旧 reflect-config.json 等) やヒント(@RegisterReflection)で明示");
        System.out.println("トレードオフ: ビルドが長い・ピークスループットは JIT(HotSpot)にやや劣る場合がある");
        System.out.println("Spring Boot 3 は AOT 処理でメタデータを生成し Native Image を公式サポートする");
    }
}
