// ■ ビュー定義（マーカークラスの階層）
public class Views {
    public static class Public {}  // 公開ビュー
    public static class Admin extends Public {}  // 管理者ビュー（Public を含む）
}

// ■ エンティティ/DTO にビューを指定
public record User(
    @JsonView(Views.Public.class) Long id,  // 両方のビューで表示
    @JsonView(Views.Public.class) String name,  // 両方のビューで表示
    @JsonView(Views.Admin.class)  String email,  // 管理者ビューのみ
    @JsonView(Views.Admin.class)  String phone,  // 管理者ビューのみ
    @JsonView(Views.Admin.class)  String role  // 管理者ビューのみ
) {}

// ■ コントローラでビューを切り替え
@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/{id}")
    @JsonView(Views.Public.class)  // POINT: 公開ビュー → id, name のみ
    public User getPublicUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/admin/{id}")
    @JsonView(Views.Admin.class)  // POINT: 管理者ビュー → 全フィールド
    public User getAdminUser(@PathVariable Long id) {
        return userService.findById(id);
    }
}

// ■ レスポンス例
// GET /api/users/1 (Public ビュー)
// → {"id": 1, "name": "Alice"}

// GET /api/users/admin/1 (Admin ビュー)
// → {"id": 1, "name": "Alice", "email": "alice@ex.com", "phone": "090...", "role": "ADMIN"}