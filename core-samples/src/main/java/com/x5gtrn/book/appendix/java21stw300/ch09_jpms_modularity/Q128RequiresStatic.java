package com.x5gtrn.book.appendix.java21stw300.ch09_jpms_modularity;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q128RequiresStatic implements Sample {
    public String chapter(){ return "09_JPMS_Modularity"; }
    public int problem(){ return 128; }
    public String title(){ return "requires static の使いどころ"; }
    public void run(){
        System.out.println("requires static org.mapstruct.processor;  // コンパイル時のみ必要");
        System.out.println("requires static : コンパイル時は必須だが、実行時は任意（無くても起動できる）");
        System.out.println("用途: 注釈プロセッサ、コンパイル時アノテーション(@Nullable等)、任意の最適化用ライブラリ");
        System.out.println("実行時に本当に使う場合は、その存在を try/catch(Class.forName等)で確認してから使う");
        System.out.println("通常の requires との違い: 実行時にモジュールグラフへ必須追加されない（オプショナル依存）");
    }
}
