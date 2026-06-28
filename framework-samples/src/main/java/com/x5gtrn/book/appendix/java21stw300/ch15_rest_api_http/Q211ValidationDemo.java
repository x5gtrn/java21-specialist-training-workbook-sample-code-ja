package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;

import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 問題211: バリデーション（@Valid）。
 * 不正なリクエストは MethodArgumentNotValidException となり 400 Bad Request に変換される。
 * 実際にアプリ自身の REST エンドポイント（POST /api/demo/users）を呼び出して確認する。
 */
@Component
public class Q211ValidationDemo implements FrameworkSample {

    private final int port;

    public Q211ValidationDemo(@Value("${server.port:8081}") int port) {
        this.port = port;
    }

    public String chapter() { return "15_REST_API_HTTP"; }
    public int problem()    { return 211; }
    public String title()   { return "リクエストバリデーションと 400 Bad Request"; }

    public void run() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String base = "http://localhost:" + port + "/api/demo/users";

        String invalid = "{\"name\":\"\",\"email\":\"not-an-email\",\"age\":-1}";
        HttpResponse<String> bad = client.send(
                HttpRequest.newBuilder(URI.create(base))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(invalid)).build(),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("[不正な入力] POST /api/demo/users");
        System.out.println("  status = " + bad.statusCode() + "  (期待: 400)");
        System.out.println("  body   = " + bad.body());

        String valid = "{\"name\":\"Alice\",\"email\":\"alice@example.com\",\"age\":30}";
        HttpResponse<String> ok = client.send(
                HttpRequest.newBuilder(URI.create(base))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(valid)).build(),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("[正常な入力] POST /api/demo/users");
        System.out.println("  status = " + ok.statusCode() + "  (期待: 201)");
        System.out.println("  body   = " + ok.body());
    }
}
