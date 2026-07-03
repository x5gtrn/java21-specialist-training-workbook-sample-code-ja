package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
@Component
public class Q204AotNativeImage implements FrameworkSample {
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 204; }
    public String title(){ return "Spring Boot AOT と Native Image の制約"; }
    public void run(){
        System.out.println("Spring Boot 3 の AOT 処理はビルド時に Bean 定義を確定し、リフレクション用メタデータを生成する");
        System.out.println("効果: GraalVM Native Image 化で 起動数十ms・低メモリ（サーバレス/スケールtoゼロ向け）");
        System.out.println("制約: 実行時の動的登録が制限される。以下は事前ヒントが必要:");
        System.out.println("  ・リフレクション/動的プロキシ/リソース/シリアライズ → RuntimeHints で登録");
        System.out.println("  ・条件付き Bean は起動時プロファイル等で分岐せず、ビルド時に固定される点に注意");
        System.out.println("開発時は通常の JVM、リリースで native をビルド＆テスト（挙動差の検証が必須）");
    }
}
