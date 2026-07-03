// レコード定義 — 追加アノテーション不要
public record UserResponse(
    Long id,
    String name,
    String email,
    LocalDateTime createdAt
) {}

// コントローラでの使用
@RestController
public class UserController {
    @GetMapping("/users/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return new UserResponse(id, "Alice", "alice@example.com", LocalDateTime.now());
    }
    // → {"id":1,"name":"Alice","email":"alice@example.com","createdAt":"2024-01-01T12:00:00"}

    @PostMapping("/users")
    public UserResponse createUser(@RequestBody UserResponse request) {
        // POINT: JSON からレコードへの自動デシリアライゼーション
        return userService.create(request);
    }
}

// カスタマイズが必要な場合のみアノテーション使用
public record UserResponse(
    Long id,
    @JsonProperty("full_name") String name,  // JSON フィールド名のカスタマイズ
    @JsonIgnore String internalCode  // シリアライゼーションから除外
) {}