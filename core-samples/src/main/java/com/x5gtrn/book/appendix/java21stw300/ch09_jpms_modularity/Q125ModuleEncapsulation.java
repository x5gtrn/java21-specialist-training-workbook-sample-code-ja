package com.x5gtrn.book.appendix.java21stw300.ch09_jpms_modularity;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q125ModuleEncapsulation implements Sample {
    public String chapter(){ return "09_JPMS_Modularity"; }
    public int problem(){ return 125; }
    public String title(){ return "JPMS モジュールのカプセル化"; }
    public void run(){
        // module-info.java の exports で公開したパッケージのみ、他モジュールから型参照できる
        System.out.println("module example.api {");
        System.out.println("    exports com.example.api;          // 公開: 外部から利用可");
        System.out.println("    // com.example.internal は非公開: exports しない限り外部はアクセス不可");
        System.out.println("}");
        System.out.println("classpath 時代の『public なら誰でも使える』を、モジュールは exports で制御する");
        System.out.println("非公開パッケージへの参照はコンパイル/実行時に拒否され、真のカプセル化を実現する");
    }
}
