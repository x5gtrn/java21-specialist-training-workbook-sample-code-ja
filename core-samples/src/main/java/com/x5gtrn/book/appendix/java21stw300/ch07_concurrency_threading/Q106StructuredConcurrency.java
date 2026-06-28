package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

/**
 * 問題106: Structured Concurrency（JEP 453, Preview）。
 * 複数のサブタスクを仮想スレッドでフォークし、join() で全完了を待ち、
 * いずれか失敗すれば throwIfFailed() で例外を投げる。--enable-preview が必要。
 */
public final class Q106StructuredConcurrency implements Sample {

    record UserData(String name, String email) {}
    record OrderHistory(List<String> orders) {}

    public String chapter() { return "07_Concurrency_Threading"; }
    public int problem()    { return 106; }
    public String title()   { return "Structured Concurrency（Preview）"; }
    @Override public boolean preview() { return true; }

    public void run() throws Exception {
        long userId = 42L;
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var userTask  = scope.fork(() -> fetchUserData(userId));
            var orderTask = scope.fork(() -> fetchOrderHistory(userId));

            scope.join();            // 全サブタスクの完了を待機
            scope.throwIfFailed();   // いずれか失敗していたら例外

            UserData user = userTask.get();
            OrderHistory orders = orderTask.get();
            System.out.println("user   = " + user);
            System.out.println("orders = " + orders.orders());
        }
    }

    private UserData fetchUserData(long id) throws InterruptedException {
        Thread.sleep(50);
        return new UserData("user-" + id, "user" + id + "@example.com");
    }

    private OrderHistory fetchOrderHistory(long id) throws InterruptedException {
        Thread.sleep(50);
        return new OrderHistory(List.of("order-1", "order-2", "order-3"));
    }
}
