package com.x5gtrn.book.appendix.java21stw300.ch09_jpms_modularity;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.ServiceLoader;
public final class Q131ServiceLoader implements Sample {
    public String chapter(){ return "09_JPMS_Modularity"; }
    public int problem(){ return 131; }
    public String title(){ return "ServiceLoader とモジュール"; }
    public void run(){
        // ServiceLoader は実装を動的に発見する（ここでは META-INF/services 経由で2実装を登録済み）
        ServiceLoader<GreetingProvider> loader = ServiceLoader.load(GreetingProvider.class);
        for (GreetingProvider p : loader)
            System.out.println("発見した実装[" + p.lang() + "] -> " + p.greet("Taro"));
        long n = ServiceLoader.load(GreetingProvider.class).stream().count();
        System.out.println("登録実装数 = " + n + "（実装の追加＝JAR追加だけで拡張でき、疎結合を保てる）");
        System.out.println("モジュール環境では module-info の `provides X with Y;` / `uses X;` で宣言する");
    }
}
