// OK: char[] を使用した安全なパスワード処理
Console console = System.console();
char[] password = console.readPassword("Password: ");  // POINT: readPassword() → char[]
try {
    boolean authenticated = authService.verify(username, password);
    if (!authenticated) {
        throw new AuthenticationException("Invalid credentials");
    }
    // ... 認証成功後の処理
} finally {
    Arrays.fill(password, '\0');  // POINT: 使用後に即座にゼロクリア
    // → メモリ上のパスワードが消去される
}

// NG: String を使用した危険なパスワード処理
String password = scanner.nextLine();  // POINT: String → 消去不可能
// → password が GC されるまでヒープ上にパスワードが平文で残存
// → ヒープダンプ、コアダンプ、メモリスキャンで読み取り可能
// → String Pool に入った場合はさらに長期間残存

// ■ なぜ Console.readPassword() は char[] を返すのか
// → Java API の設計者がセキュリティ上の理由で char[] を選択
// → 呼び出し側が使用後にゼロクリアすることを期待

// ■ さらなるセキュリティベストプラクティス
// 1. パスワードのハッシュ化（保存時）— Spring Security の PasswordEncoder を使う
//    （jBCrypt 等の外部ライブラリを直接呼ぶのではなく、Spring 標準に寄せる）
PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
String hashedPassword = encoder.encode(new String(password));
// POINT: 平文パスワードは保存しない

// 2. パスワード照合（生パスワード vs 保存済みハッシュ）
boolean matches = encoder.matches(new String(inputPassword), storedHash);

// 3. POINT: 一時的に String に変換した場合もすぐに参照を解放
// → ただし String のメモリクリアは不可能なため、char[] での処理を最大限に