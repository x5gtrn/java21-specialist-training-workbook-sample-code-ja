package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
@Component
public class Q207ContentNegotiation implements FrameworkSample {
    record Book(String title, int year) {}
    public String chapter(){ return "15_REST_API_HTTP"; }
    public int problem(){ return 207; }
    public String title(){ return "コンテンツネゴシエーション"; }
    public void run() throws Exception {
        Book book = new Book("Java 21", 2023);
        // サーバは Accept ヘッダを見て表現(JSON/XML等)を選ぶ。ここでは JSON 表現を示す。
        System.out.println("Accept: application/json -> " + new ObjectMapper().writeValueAsString(book));
        System.out.println("Accept: application/xml  -> <Book><title>Java 21</title><year>2023</year></Book> (相当)");
        System.out.println("Spring MVC は produces と HttpMessageConverter 群で Accept に応じた表現を自動選択する");
        System.out.println("同一リソース・同一URLで、クライアントの要求形式に合わせて出力を切り替える");
    }
}
