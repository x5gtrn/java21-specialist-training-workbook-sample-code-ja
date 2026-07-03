package com.x5gtrn.book.appendix.java21stw300.ch10_design_patterns;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q138BuilderWithRecords implements Sample {
    record HttpRequest(String method, String url, int timeoutSec, boolean followRedirects) {
        static Builder builder(String url){ return new Builder(url); }
        static final class Builder {
            private final String url;
            private String method = "GET";
            private int timeoutSec = 30;
            private boolean followRedirects = true;
            Builder(String url){ this.url = url; }
            Builder method(String m){ this.method = m; return this; }
            Builder timeoutSec(int t){ this.timeoutSec = t; return this; }
            Builder followRedirects(boolean f){ this.followRedirects = f; return this; }
            HttpRequest build(){ return new HttpRequest(method, url, timeoutSec, followRedirects); }
        }
    }
    public String chapter(){return "10_Design_Patterns";}
    public int problem(){return 138;}
    public String title(){return "Builder パターンと Records";}
    public void run(){
        HttpRequest req = HttpRequest.builder("https://example.com/api")
                .method("POST").timeoutSec(10).followRedirects(false).build();
        System.out.println(req);
    }
}
