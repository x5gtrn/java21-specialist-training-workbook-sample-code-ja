package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Cacheable のデモ用サービス（第14章 問題194）。
 * findProductName は同じ id の 2 回目以降はキャッシュから返り、DB アクセス（カウンタ）は増えない。
 */
@Service
public class CatalogService {

    private final AtomicInteger dbAccessCount = new AtomicInteger();

    @Cacheable("productNames")
    public String findProductName(long id) {
        dbAccessCount.incrementAndGet();
        try {
            Thread.sleep(50); // 重い DB アクセスを模擬
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        return "Product-" + id;
    }

    public int dbAccessCount() {
        return dbAccessCount.get();
    }
}
