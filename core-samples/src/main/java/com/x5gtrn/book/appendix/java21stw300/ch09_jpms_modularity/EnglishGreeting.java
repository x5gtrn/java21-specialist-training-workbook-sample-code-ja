package com.x5gtrn.book.appendix.java21stw300.ch09_jpms_modularity;
public final class EnglishGreeting implements GreetingProvider {
    public String lang(){ return "en"; }
    public String greet(String name){ return "Hello, " + name + "!"; }
}
