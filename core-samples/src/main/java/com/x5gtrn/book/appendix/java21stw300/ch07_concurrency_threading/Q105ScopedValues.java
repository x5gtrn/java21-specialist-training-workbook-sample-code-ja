package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;

/**
 * 問題105: Scoped Values（JEP 446, Preview）。
 * ScopedValue.where(...).run(...) のスコープ内でのみ値が参照でき、呼び出し先にも伝播する。
 * スコープを抜けると自動的にバインディングが解除される。--enable-preview が必要。
 */
public final class Q105ScopedValues implements Sample {

    record UserContext(String name) {}

    private static final ScopedValue<UserContext> CURRENT_USER = ScopedValue.newInstance();

    public String chapter() { return "07_Concurrency_Threading"; }
    public int problem()    { return 105; }
    public String title()   { return "Scoped Values（Preview）"; }
    @Override public boolean preview() { return true; }

    public void run() {
        UserContext user = new UserContext("Alice");

        ScopedValue.where(CURRENT_USER, user).run(() -> {
            processOrder();    // スコープ内 -> get() できる
            sendNotification();
        });

        // スコープ外: バインドされていない
        System.out.println("スコープ外で isBound = " + CURRENT_USER.isBound());
    }

    private void processOrder() {
        System.out.println("processOrder    : user = " + CURRENT_USER.get().name());
    }

    private void sendNotification() {
        System.out.println("sendNotification: user = " + CURRENT_USER.get().name());
    }
}
