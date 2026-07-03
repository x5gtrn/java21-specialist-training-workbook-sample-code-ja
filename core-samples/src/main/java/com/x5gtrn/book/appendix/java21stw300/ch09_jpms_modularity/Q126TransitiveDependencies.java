package com.x5gtrn.book.appendix.java21stw300.ch09_jpms_modularity;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q126TransitiveDependencies implements Sample {
    public String chapter(){ return "09_JPMS_Modularity"; }
    public int problem(){ return 126; }
    public String title(){ return "推移的依存関係（Transitive Dependencies）"; }
    public void run(){
        System.out.println("module app { requires service; }");
        System.out.println("module service { requires transitive model; }  // model を再公開");
        System.out.println("→ app は自分で requires model を書かずとも model の型を使える（暗黙の可読性）");
        System.out.println("使いどころ: 公開APIの戻り値/引数に別モジュールの型が現れる場合は requires transitive にする");
        System.out.println("そうしないと利用側が毎回 requires を追加する必要が生じる（APIの使い勝手が悪化）");
    }
}
