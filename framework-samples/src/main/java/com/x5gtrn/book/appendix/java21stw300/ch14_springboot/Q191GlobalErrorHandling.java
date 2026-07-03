package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Component
public class Q191GlobalErrorHandling implements FrameworkSample {
    private final int port;
    public Q191GlobalErrorHandling(@Value("${server.port:8081}") int port){ this.port = port; }
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 191; }
    public String title(){ return "グローバルエラーハンドリング（@ControllerAdvice）"; }
    public void run() throws Exception {
        // @RestControllerAdvice が全コントローラ横断で例外を捕捉し、統一エラー形式へ変換する
        HttpClient client = HttpClient.newHttpClient();
        String body = "{\"name\":\"\",\"email\":\"bad\",\"age\":-5}";
        HttpResponse<String> res = client.send(
                HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/demo/users"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body)).build(),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("status = " + res.statusCode() + "（@ControllerAdvice が 400 に統一変換）");
        System.out.println("body   = " + res.body());
        System.out.println("各コントローラで try-catch を散らさず、横断的に一貫したエラー応答を返せる");
    }
}
