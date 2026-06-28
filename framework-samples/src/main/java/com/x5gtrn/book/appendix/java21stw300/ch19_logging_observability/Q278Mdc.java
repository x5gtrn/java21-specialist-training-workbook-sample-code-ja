package com.x5gtrn.book.appendix.java21stw300.ch19_logging_observability;

import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * 問題278: SLF4J と MDC（Mapped Diagnostic Context）。
 * MDC にリクエスト単位の文脈（traceId / userId）を入れると、ログパターンの %X{key} で出力できる。
 * MDC はスレッド単位で保持され、処理後に clear する必要がある。
 */
@Component
public class Q278Mdc implements FrameworkSample {

    public String chapter() { return "19_Logging_Observability"; }
    public int problem()    { return 278; }
    public String title()   { return "SLF4J と MDC によるコンテキスト付きログ"; }

    public void run() {
        MDC.put("traceId", "abc12345");
        MDC.put("userId", "user-789");

        // 実際のログでは pattern の %X{traceId} %X{userId} で以下と同じ内容が出力される
        String logLine = String.format("%s [%s] [%s] INFO  OrderService - Processing order...",
                LocalTime.now().withNano(0), MDC.get("traceId"), MDC.get("userId"));
        System.out.println(logLine);
        System.out.println("MDC の内容    : " + MDC.getCopyOfContextMap());

        MDC.clear();
        System.out.println("MDC.clear() 後 : " + MDC.getCopyOfContextMap());
    }
}
