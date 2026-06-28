package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;

import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 問題214: HTTP キャッシュ（Cache-Control / ETag）。
 * 1 回目の GET で ETag を受け取り、2 回目に If-None-Match を付けると 304 Not Modified が返る。
 * アプリ自身の GET /api/demo/config を呼び出して確認する。
 */
@Component
public class Q214HttpCachingDemo implements FrameworkSample {

    private final int port;

    public Q214HttpCachingDemo(@Value("${server.port:8081}") int port) {
        this.port = port;
    }

    public String chapter() { return "15_REST_API_HTTP"; }
    public int problem()    { return 214; }
    public String title()   { return "HTTP キャッシュ（ETag と 304 Not Modified）"; }

    public void run() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String url = "http://localhost:" + port + "/api/demo/config";

        HttpResponse<String> first = client.send(
                HttpRequest.newBuilder(URI.create(url)).GET().build(),
                HttpResponse.BodyHandlers.ofString());
        String etag = first.headers().firstValue("ETag").orElse("(none)");
        System.out.println("1回目 GET                 : status=" + first.statusCode()
                + ", ETag=" + etag + ", Cache-Control=" + first.headers().firstValue("Cache-Control").orElse("-"));

        HttpResponse<String> second = client.send(
                HttpRequest.newBuilder(URI.create(url))
                        .header("If-None-Match", etag).GET().build(),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("2回目 GET (If-None-Match) : status=" + second.statusCode()
                + "  (期待: 304 Not Modified, ボディなし)");
    }
}
