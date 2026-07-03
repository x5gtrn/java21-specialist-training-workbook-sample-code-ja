package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Map;
import java.util.Optional;
public final class Q71OptionalExceptionAvoidance implements Sample {
    public String chapter(){return "05_Streams_Lambda_Functional";}
    public int problem(){return 71;}
    public String title(){return "Optional による例外回避";}
    public void run(){
        Map<String,String> config = Map.of("host", "localhost", "port", "8080");
        // 存在しないキーでも例外にせず、map/filter/orElse で安全に処理
        int port = Optional.ofNullable(config.get("port")).map(Integer::parseInt).filter(p -> p > 0).orElse(80);
        int timeout = Optional.ofNullable(config.get("timeout")).map(Integer::parseInt).orElse(30);
        System.out.println("port    = " + port + "（設定あり）");
        System.out.println("timeout = " + timeout + "（設定なし -> デフォルト）");
    }
}
