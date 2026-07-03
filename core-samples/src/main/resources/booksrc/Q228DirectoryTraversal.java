@GetMapping("/api/files/{filename}")
public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
    Path baseDir = Path.of("/app/uploads").toAbsolutePath().normalize();

    // POINT: Step 1: 結合
    Path requestedPath = baseDir.resolve(filename);
    // "../../etc/passwd" → /app/uploads/../../etc/passwd

    // POINT: Step 2: 正規化
    Path normalizedPath = requestedPath.normalize();
    // /app/uploads/../../etc/passwd → /etc/passwd

    // POINT: Step 3: ベースディレクトリ内か検証
    if (!normalizedPath.startsWith(baseDir)) {
        log.warn("Path traversal attempt: {} → {}", filename, normalizedPath);
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
    }
    // /etc/passwd は /app/uploads で始まらない → POINT: 攻撃検出！

    // 安全なファイル提供
    if (!Files.exists(normalizedPath)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    Resource resource = new FileSystemResource(normalizedPath);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                normalizedPath.getFileName() + "\"")
        .body(resource);
}

// ■ 追加の防御策（多層防御）
// 1. ファイル名から危険文字を除去: filename.replaceAll("[^a-zA-Z0-9._-]", "")
// 2. アップロード時に UUID でリネーム: UUID.randomUUID() + extension
// 3. シンボリックリンクの解決: Path.toRealPath() でシンボリックリンクも解決