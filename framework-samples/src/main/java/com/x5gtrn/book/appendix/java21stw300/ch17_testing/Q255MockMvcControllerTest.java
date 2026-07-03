package com.x5gtrn.book.appendix.java21stw300.ch17_testing;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Component
public class Q255MockMvcControllerTest implements FrameworkSample {
    private final int port;
    public Q255MockMvcControllerTest(@Value("${server.port:8081}") int port){ this.port = port; }
    public String chapter(){ return "17_Testing"; }
    public int problem(){ return 255; }
    public String title(){ return "MockMvc によるコントローラテスト"; }
    public void run() throws Exception {
        // MockMvc はサーバを立てずにMVCを検証する。ここでは実エンドポイントを叩いて同等の検証観点を示す。
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> res = client.send(
            HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/demo/config")).GET().build(),
            HttpResponse.BodyHandlers.ofString());
        System.out.println("GET /api/demo/config -> status " + res.statusCode()
            + ", ETag=" + res.headers().firstValue("ETag").orElse("-"));
        System.out.println("検証観点: ステータス/ヘッダ/JSONボディを assert する");
        System.out.println("MockMvc: mockMvc.perform(get(\"/...\")).andExpect(status().isOk()).andExpect(jsonPath(...))");
        System.out.println("利点: サーブレットコンテナ起動なしでコントローラ層を高速にテストできる");
    }
}
