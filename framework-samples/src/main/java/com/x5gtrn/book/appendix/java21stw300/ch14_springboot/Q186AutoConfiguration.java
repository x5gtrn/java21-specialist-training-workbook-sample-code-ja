package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;

import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 問題186: Spring Boot の自動構成。
 * クラスパスに H2 と spring-boot-starter-data-jpa があると、DataSource（HikariCP）が
 * 自動構成される。開発者が DataSource を定義しなければ @ConditionalOnMissingBean が成立して
 * デフォルトが採用される。
 */
@Component
public class Q186AutoConfiguration implements FrameworkSample {

    private final DataSource dataSource;
    private final Environment env;

    public Q186AutoConfiguration(DataSource dataSource, Environment env) {
        this.dataSource = dataSource;
        this.env = env;
    }

    public String chapter() { return "14_SpringBoot"; }
    public int problem()    { return 186; }
    public String title()   { return "Spring Boot の自動構成（DataSource）"; }

    public void run() throws Exception {
        System.out.println("自動構成された DataSource 実装 : " + dataSource.getClass().getName());
        System.out.println("spring.datasource.url         : " + env.getProperty("spring.datasource.url"));
        try (Connection c = dataSource.getConnection()) {
            System.out.println("DB 製品名                    : " + c.getMetaData().getDatabaseProductName());
            System.out.println("実際の JDBC URL              : " + c.getMetaData().getURL());
        }
    }
}
