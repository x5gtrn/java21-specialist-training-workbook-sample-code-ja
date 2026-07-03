package com.x5gtrn.book.appendix.java21stw300.ch09_jpms_modularity;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q127QualifiedExports implements Sample {
    public String chapter(){ return "09_JPMS_Modularity"; }
    public int problem(){ return 127; }
    public String title(){ return "JPMS モジュール宣言（修飾 exports）"; }
    public void run(){
        System.out.println("exports com.example.internal to com.example.test, com.example.tooling;");
        System.out.println("修飾 exports は『特定モジュールにだけ』公開する（一般には非公開のまま）");
        System.out.println("用途: 実装内部を、信頼する自社の別モジュール(テスト/ツール)にのみ限定公開する");
        System.out.println("全公開の exports との違い: 公開範囲を必要最小限に絞り、内部結合の漏れを防ぐ");
    }
}
