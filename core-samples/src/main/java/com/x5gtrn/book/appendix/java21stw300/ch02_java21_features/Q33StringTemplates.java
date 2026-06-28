package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;

/**
 * 問題33: String Templates（JEP 430, Preview）。
 * STR プロセッサで \{式} を埋め込める。コンパイル・実行には --enable-preview が必要。
 */
public final class Q33StringTemplates implements Sample {

    record User(String name, double balance) {}

    public String chapter() { return "02_Java21_Features"; }
    public int problem()    { return 33; }
    public String title()   { return "String Templates（Preview）"; }
    @Override public boolean preview() { return true; }

    public void run() {
        String name = "Alice";
        double price = 29.99;
        String msg = STR."Hello \{name}, your total is $\{price}";
        System.out.println(msg);

        var user = new User("Bob", -50.0);
        String info = STR."""
            Name:    \{user.name()}
            Balance: \{user.balance() > 0 ? "Positive" : "Negative"}""";
        System.out.println(info);
    }
}
