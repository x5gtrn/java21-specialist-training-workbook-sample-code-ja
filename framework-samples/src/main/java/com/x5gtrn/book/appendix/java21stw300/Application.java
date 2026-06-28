package com.x5gtrn.book.appendix.java21stw300;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 「Java 21 Specialist Training Workbook - Sample Code（フレームワーク編）」の Spring Boot アプリ。
 *
 * <p>このクラスはパッケージ階層の最上位（com.x5gtrn.book.appendix.java21stw300）に置く。
 * Spring Boot はこのクラスのパッケージを基点として、コンポーネント走査・JPA リポジトリ走査・
 * エンティティ走査を行うため、配下の springapp / domain / repo / web / chXX_* がすべて対象になる。</p>
 */
@SpringBootApplication
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
