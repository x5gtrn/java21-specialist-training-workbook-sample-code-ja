// Native Image で注意が必要なリフレクション
// reflect-config.json が必要になるケース
[
  {
    "name": "com.example.model.User",
    "allDeclaredConstructors": true,
    "allPublicMethods": true,
    "allDeclaredFields": true
  }
]

// GraalVM のビルド時ヒント（Spring Boot 3.x）
@RegisterReflectionForBinding(User.class)  // Spring のアノテーション
public class UserController { /* 実装省略 */ }