// A: SecureRandom の使用
SecureRandom secureRandom = SecureRandom.getInstanceStrong();  // 最強のアルゴリズム
byte[] tokenBytes = new byte[32];
secureRandom.nextBytes(tokenBytes);
String sessionToken = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);

// NG: java.util.Random は予測可能（セキュリティ用途に不適切）
// Random random = new Random(); // NG

// B: パスワードハッシュ（Spring Security の例）
// NG: 単純な SHA-256 はパスワード保存に不適切
// MessageDigest md = MessageDigest.getInstance("SHA-256");
// byte[] hash = md.digest(password.getBytes()); // NG: ソルトなし、高速すぎる

// OK: bcrypt（Spring Security）
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);  // コスト因子12
String hashed = encoder.encode("mypassword");
boolean matches = encoder.matches("mypassword", hashed);

// C: キー管理
// NG: ソースコードに暗号化キーをハードコード
// private static final String SECRET_KEY = "mySecretKey123"; // NG

// OK: 環境変数や Vault から取得
// String key = System.getenv("ENCRYPTION_KEY");
// String key = vaultClient.readSecret("app/encryption-key");