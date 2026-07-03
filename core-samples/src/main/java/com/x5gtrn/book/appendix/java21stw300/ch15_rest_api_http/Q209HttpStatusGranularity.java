package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q209HttpStatusGranularity implements Sample {
    enum Outcome { CREATED, VALIDATION_ERROR, NOT_FOUND, CONFLICT, UNAUTHORIZED, SERVER_ERROR }
    public String chapter(){return "15_REST_API_HTTP";}
    public int problem(){return 209;}
    public String title(){return "HTTP Status Code の粒度";}
    public void run(){
        for (Outcome o : Outcome.values())
            System.out.printf("%-16s -> %d%n", o, statusFor(o));
        System.out.println("200/500 の二択でなく、意味に応じた粒度で返すとクライアントが適切に分岐できる");
    }
    private int statusFor(Outcome o){
        return switch (o) {
            case CREATED -> 201;
            case VALIDATION_ERROR -> 400;
            case UNAUTHORIZED -> 401;
            case NOT_FOUND -> 404;
            case CONFLICT -> 409;
            case SERVER_ERROR -> 500;
        };
    }
}
