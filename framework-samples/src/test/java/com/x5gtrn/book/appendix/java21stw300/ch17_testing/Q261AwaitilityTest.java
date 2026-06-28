package com.x5gtrn.book.appendix.java21stw300.ch17_testing;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 問題261: TDD における非同期通知のテスト。
 * Thread.sleep で固定待機する代わりに、Awaitility で「条件が満たされるまで」待つ。
 * 実行: ./gradlew :framework-samples:test
 */
class Q261AwaitilityTest {

    @Test
    void awaitsAsyncNotification() {
        AtomicBoolean delivered = new AtomicBoolean(false);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        try {
            // 200ms 後に非同期で通知が届く
            scheduler.schedule(() -> delivered.set(true), 200, TimeUnit.MILLISECONDS);

            await().atMost(Duration.ofSeconds(2))
                    .pollInterval(Duration.ofMillis(50))
                    .until(delivered::get);

            assertTrue(delivered.get());
        } finally {
            scheduler.shutdown();
        }
    }
}
