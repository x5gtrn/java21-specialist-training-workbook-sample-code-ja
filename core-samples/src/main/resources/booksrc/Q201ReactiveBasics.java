// ■ WebFlux のコントローラ
@RestController
public class NotificationController {

    @GetMapping(value = "/notifications/stream",
                produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Notification> stream(@RequestParam String userId) {
        // POINT: SSE（Server-Sent Events）でリアルタイムストリーミング
        return notificationService.getNotificationStream(userId);
        // → ノンブロッキング、スレッドを占有しない
    }

    @GetMapping("/notifications/{id}")
    public Mono<Notification> getById(@PathVariable String id) {
        return notificationService.findById(id);
        // Mono → 0 or 1 要素（Optional のリアクティブ版）
    }

    @PostMapping("/notifications")
    public Mono<Notification> create(@RequestBody Mono<Notification> body) {
        return body.flatMap(notificationService::save);
    }
}

// ■ Mono<T> と Flux<T> は遅延実行（lazy）
Mono<User> userMono = userService.findById(id);
// ↑ この時点では何も実行されない！
// サブスクライブされて初めて実行される

userMono.subscribe(user -> log.info("Found: {}", user));
// ↑ ここで初めて実行される

// ■ Spring MVC vs WebFlux の使い分け
// Spring MVC: ブロッキング I/O（JDBC 等）中心、Servlet API 依存
// WebFlux: ノンブロッキング I/O（R2DBC, WebClient 等）中心
// → 両方を Spring Boot 内で共存させることも可能（推奨はどちらか一方）