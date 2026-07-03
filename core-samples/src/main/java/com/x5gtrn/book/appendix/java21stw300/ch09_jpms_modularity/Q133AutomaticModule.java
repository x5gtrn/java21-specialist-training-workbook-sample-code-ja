package com.x5gtrn.book.appendix.java21stw300.ch09_jpms_modularity;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q133AutomaticModule implements Sample {
    public String chapter(){ return "09_JPMS_Modularity"; }
    public int problem(){ return 133; }
    public String title(){ return "JPMS 移行と Automatic Module のリスク"; }
    public void run(){
        System.out.println("module-info の無い JAR をモジュールパスに置くと『自動モジュール』になる");
        System.out.println("  ・モジュール名は JAR ファイル名から自動導出（例: guava-31.jar -> guava）");
        System.out.println("  ・全ての他モジュールを requires し、全パッケージを exports 扱いにする");
        System.out.println("リスク: ファイル名変更で自動モジュール名が変わり、依存が壊れる（名前が不安定）");
        System.out.println("緩和: Automatic-Module-Name をマニフェストに明記 / 依存が正式モジュール化されるのを待つ");
        System.out.println("段階移行: まず classpath、次にボトムアップ/トップダウンで module-info を導入する");
    }
}
