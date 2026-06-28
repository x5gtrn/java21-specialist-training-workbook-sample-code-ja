package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;

/**
 * 問題9: instanceof のパターンマッチングと束縛変数のスコープ。
 *
 * value が String でなければパターンは false。&& と組み合わせると安全に絞り込める。
 */
public final class Q09PatternInstanceof implements Sample {

    public String chapter() { return "01_Language_OOP"; }
    public int problem()    { return 9; }
    public String title()   { return "instanceof パターンマッチング"; }

    public void run() {
        describe("Hello, Java 21 World!"); // String かつ length > 10
        describe("short");                 // String だが length <= 10
        describe(42);                      // String ではない
        describe(null);                    // null
    }

    private void describe(Object value) {
        if (value instanceof String s && s.length() > 10) {
            System.out.println("長い文字列: " + s.toUpperCase());
        } else if (value instanceof String s) {
            System.out.println("短い文字列: \"" + s + "\" (length=" + s.length() + ")");
        } else {
            System.out.println("String ではない: " + value);
        }
    }
}
