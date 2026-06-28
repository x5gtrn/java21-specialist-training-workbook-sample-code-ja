package com.x5gtrn.book.appendix.java21stw300.framework;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 「Java 21 Specialist Training Workbook - Sample Code」の Web フロントエンド兼 API サーバ。
 *
 * <p>外部ライブラリに依存せず、JDK 内蔵の {@link com.sun.net.httpserver.HttpServer} だけで動作する。</p>
 *
 * <ul>
 *   <li>{@code GET /}              … サンプル一覧 UI（HTML）</li>
 *   <li>{@code GET /api/samples}   … 全サンプルのメタ情報＋ソースコード（JSON）</li>
 *   <li>{@code GET /api/run?id=X}  … 指定サンプルを実行し、出力を返す（JSON）</li>
 * </ul>
 */
public final class WorkbookServer {

    public static void main(String[] args) throws IOException {
        int port = port();
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);

        server.createContext("/", WorkbookServer::handleRoot);
        server.createContext("/api/samples", WorkbookServer::handleSamples);
        server.createContext("/api/run", WorkbookServer::handleRun);

        server.setExecutor(null); // default executor
        server.start();

        System.out.println("================================================================");
        System.out.println(" Java 21 Specialist Training Workbook - Sample Code");
        System.out.println(" Server started: http://localhost:" + port);
        System.out.println(" Registered samples: " + SampleRegistry.ALL.size());
        System.out.println("================================================================");
    }

    private static int port() {
        String env = System.getenv("PORT");
        if (env != null && !env.isBlank()) {
            try {
                return Integer.parseInt(env.trim());
            } catch (NumberFormatException ignored) {
                // fall through to default
            }
        }
        return 8080;
    }

    // ---- handlers -------------------------------------------------------

    private static void handleRoot(HttpExchange ex) throws IOException {
        if (!"/".equals(ex.getRequestURI().getPath())) {
            send(ex, 404, "text/plain; charset=utf-8", "Not Found".getBytes(StandardCharsets.UTF_8));
            return;
        }
        String html = readResource("/web/index.html");
        if (html == null) {
            send(ex, 500, "text/plain; charset=utf-8", "index.html not found".getBytes(StandardCharsets.UTF_8));
            return;
        }
        send(ex, 200, "text/html; charset=utf-8", html.getBytes(StandardCharsets.UTF_8));
    }

    private static void handleSamples(HttpExchange ex) throws IOException {
        StringBuilder json = new StringBuilder();
        json.append('[');
        boolean first = true;
        for (Sample s : SampleRegistry.ALL) {
            if (!first) {
                json.append(',');
            }
            first = false;
            String source = sourceOf(s);
            json.append('{')
                .append("\"id\":").append(jsonString(s.id())).append(',')
                .append("\"chapter\":").append(jsonString(s.chapter())).append(',')
                .append("\"problem\":").append(s.problem()).append(',')
                .append("\"title\":").append(jsonString(s.title())).append(',')
                .append("\"preview\":").append(s.preview()).append(',')
                .append("\"source\":").append(jsonString(source))
                .append('}');
        }
        json.append(']');
        send(ex, 200, "application/json; charset=utf-8", json.toString().getBytes(StandardCharsets.UTF_8));
    }

    private static void handleRun(HttpExchange ex) throws IOException {
        Map<String, String> q = queryParams(ex.getRequestURI().getRawQuery());
        String id = q.get("id");
        Sample target = null;
        if (id != null) {
            for (Sample s : SampleRegistry.ALL) {
                if (s.id().equals(id)) {
                    target = s;
                    break;
                }
            }
        }
        if (target == null) {
            String body = "{\"error\":true,\"output\":" + jsonString("Unknown sample id: " + id) + ",\"millis\":0}";
            send(ex, 404, "application/json; charset=utf-8", body.getBytes(StandardCharsets.UTF_8));
            return;
        }
        SampleRunner.Result result = SampleRunner.run(target);
        String body = "{"
                + "\"error\":" + result.error() + ","
                + "\"millis\":" + result.millis() + ","
                + "\"output\":" + jsonString(result.output())
                + "}";
        send(ex, 200, "application/json; charset=utf-8", body.getBytes(StandardCharsets.UTF_8));
    }

    // ---- helpers --------------------------------------------------------

    /** 本文の Java コードブロック（booksrc にバンドル）を読む。複数ブロックは区切りトークンで連結。 */
    private static String sourceOf(Sample s) {
        String path = "/booksrc/" + s.id() + ".java";
        String src = readResource(path);
        return src != null ? src : "// (本文コード未登録)";
    }

    private static String readResource(String path) {
        try (InputStream in = WorkbookServer.class.getResourceAsStream(path)) {
            if (in == null) {
                return null;
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }

    private static Map<String, String> queryParams(String rawQuery) {
        Map<String, String> map = new HashMap<>();
        if (rawQuery == null || rawQuery.isBlank()) {
            return map;
        }
        for (String pair : rawQuery.split("&")) {
            int eq = pair.indexOf('=');
            if (eq >= 0) {
                String k = URLDecoder.decode(pair.substring(0, eq), StandardCharsets.UTF_8);
                String v = URLDecoder.decode(pair.substring(eq + 1), StandardCharsets.UTF_8);
                map.put(k, v);
            }
        }
        return map;
    }

    private static void send(HttpExchange ex, int status, String contentType, byte[] body) throws IOException {
        ex.getResponseHeaders().set("Content-Type", contentType);
        ex.sendResponseHeaders(status, body.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(body);
        }
    }

    /** 文字列を JSON 文字列リテラル（前後のダブルクォート込み）にエスケープする。 */
    private static String jsonString(String s) {
        if (s == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        sb.append('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"'  -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                case '\b' -> sb.append("\\b");
                case '\f' -> sb.append("\\f");
                default -> {
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        sb.append('"');
        return sb.toString();
    }
}
