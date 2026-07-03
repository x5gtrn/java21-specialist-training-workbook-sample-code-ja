Path base = Path.of("/app/data");
Path user = Path.of("../config/secret.txt");
Path resolved = base.resolve(user);
Path normalized = resolved.normalize();
@@BLOCK@@
Path base = Path.of("/app/data");
Path user = Path.of("../config/secret.txt");

// resolve: ベースパスにユーザーパスを連結
Path resolved = base.resolve(user);
System.out.println(resolved);  // /app/data/../config/secret.txt

// normalize: 冗長な要素を除去（ファイルシステム非アクセス）
Path normalized = resolved.normalize();
System.out.println(normalized);  // /app/config/secret.txt

// POINT: セキュリティ注意: パストラバーサル防止
Path safePath = base.resolve(user).normalize();
if (!safePath.startsWith(base)) {
    throw new SecurityException("Path traversal detected: " + safePath);
}
// /app/config/secret.txt は /app/data で始まらない → 検出！

// toRealPath: シンボリックリンクを解決し、存在確認も行う（ファイルシステムアクセスあり）
Path real = resolved.toRealPath();  // IOException を投げる可能性