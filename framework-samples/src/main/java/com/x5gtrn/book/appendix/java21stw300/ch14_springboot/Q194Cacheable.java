package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;

import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;

/**
 * 問題194: @Cacheable によるキャッシュ。
 * 同じ id に対する 2 回目の呼び出しはキャッシュから返り、内部の DB アクセス回数は増えない。
 */
@Component
public class Q194Cacheable implements FrameworkSample {

    private final CatalogService catalog;

    public Q194Cacheable(CatalogService catalog) {
        this.catalog = catalog;
    }

    public String chapter() { return "14_SpringBoot"; }
    public int problem()    { return 194; }
    public String title()   { return "@Cacheable によるキャッシュ戦略"; }

    public void run() {
        long t1 = System.nanoTime();
        String a = catalog.findProductName(1);
        long e1 = (System.nanoTime() - t1) / 1_000_000;

        long t2 = System.nanoTime();
        String b = catalog.findProductName(1); // キャッシュヒット
        long e2 = (System.nanoTime() - t2) / 1_000_000;

        catalog.findProductName(2); // 別キー -> DB アクセス

        System.out.println("1回目 findProductName(1) : " + a + "  (" + e1 + " ms)");
        System.out.println("2回目 findProductName(1) : " + b + "  (" + e2 + " ms, キャッシュヒット)");
        System.out.println("DB アクセス回数          : " + catalog.dbAccessCount() + "  (id=1 が1回 + id=2 が1回)");
    }
}
