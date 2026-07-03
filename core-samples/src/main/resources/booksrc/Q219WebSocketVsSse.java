// ■ SSE（Server-Sent Events）— サーバー→クライアント単方向
@GetMapping(value = "/events/stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<ServerSentEvent<Notification>> eventStream(
        @RequestParam String userId) {
    return notificationService.getStream(userId)
        .map(n -> ServerSentEvent.<Notification>builder()
            .id(n.id().toString())
            .event("notification")
            .data(n)
            .retry(Duration.ofSeconds(5))  // 再接続間隔のヒント
            .build());
}

// ブラウザ側（JavaScript）
// const source = new EventSource('/events/stream?userId=123');
// source.onmessage = (event) => { updateUI(JSON.parse(event.data)); };
// POINT: 接続断時にブラウザが自動的に再接続

// ■ WebSocket — 双方向
@Configuration @EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(), "/ws/chat")
                .setAllowedOrigins("https://app.example.com");
    }
}

// ■ 使い分けの判断基準
// SSE:      通知プッシュ、ダッシュボード、株価更新、ログストリーミング
// → サーバー→クライアントの一方向で十分な場合
// → HTTP/2 との相性が良い、プロキシ/ファイアウォール通過が容易
// → 自動再接続が組み込み
//
// WebSocket: チャット、共同編集、マルチプレイヤーゲーム、双方向データ同期
// → クライアント→サーバーのリアルタイム送信も必要な場合
// → 独自プロトコルへのアップグレードが必要