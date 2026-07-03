package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
public final class Q205JavaHttpClient implements Sample {
    public String chapter(){return "15_REST_API_HTTP";}
    public int problem(){return 205;}
    public String title(){return "Java HttpClient API";}
    public void run() throws Exception {
        // 検証用に自プロセス内へ簡易サーバを立てる（外部依存なしで自己完結）
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/hello", ex -> {
            byte[] body = "{\"message\":\"hello\"}".getBytes(StandardCharsets.UTF_8);
            ex.getResponseHeaders().add("Content-Type", "application/json");
            ex.sendResponseHeaders(200, body.length);
            try (var os = ex.getResponseBody()) { os.write(body); }
        });
        server.start();
        int port = server.getAddress().getPort();
        try {
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(2)).build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://127.0.0.1:" + port + "/hello"))
                    .header("Accept", "application/json").GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("status = " + response.statusCode());
            System.out.println("body   = " + response.body());
        } finally { server.stop(0); }
    }
}
