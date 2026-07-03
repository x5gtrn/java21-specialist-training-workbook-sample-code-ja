public record CreateUserRequest(
    @NotBlank @Size(min = 2, max = 50) String name,
    @Email String email,
    @Min(18) @Max(150) int age
) {}
@@BLOCK@@
// コントローラでの使用
@PostMapping("/users")
public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest request) {
    // POINT: @Valid がないとバリデーションが実行されない
    // バリデーション失敗時は MethodArgumentNotValidException が自動スロー
    return ResponseEntity.ok(userService.create(request));
}

// @NotNull vs @NotEmpty vs @NotBlank
String s1 = null;  // @NotNull: NG: , @NotEmpty: NG: , @NotBlank: NG:
String s2 = "";  // @NotNull: OK: , @NotEmpty: NG: , @NotBlank: NG:
String s3 = "   ";  // @NotNull: OK: , @NotEmpty: OK: , @NotBlank: NG:
String s4 = "hello";  // @NotNull: OK: , @NotEmpty: OK: , @NotBlank: OK:

// @Min/@Max vs @Size
@Min(18) int age;  // 数値の範囲（18以上）→ OK
@Size(min=2, max=50) String name;  // 文字列/コレクションの長さ → OK
// @Size(min=2) int value; // NG: コンパイルは通るが実行時エラー（int に @Size は不適切）