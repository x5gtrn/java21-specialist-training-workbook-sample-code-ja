package com.x5gtrn.book.appendix.java21stw300.ch09_jpms_modularity;
/** ServiceLoader デモ用サービスインターフェース（問題131）。 */
public interface GreetingProvider {
    String lang();
    String greet(String name);
}
