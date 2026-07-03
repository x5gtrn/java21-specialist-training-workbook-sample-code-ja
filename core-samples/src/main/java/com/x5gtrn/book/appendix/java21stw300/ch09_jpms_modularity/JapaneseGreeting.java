package com.x5gtrn.book.appendix.java21stw300.ch09_jpms_modularity;
public final class JapaneseGreeting implements GreetingProvider {
    public String lang(){ return "ja"; }
    public String greet(String name){ return name + "さん、こんにちは！"; }
}
