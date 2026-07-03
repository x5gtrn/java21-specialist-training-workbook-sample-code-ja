@PostMapping("/api/files/upload")
public ResponseEntity<FileMetadata> upload(
        @RequestParam("file") MultipartFile file) {  // POINT: B

    // POINT: A: サーバーサイドバリデーション（クライアント側は信頼しない）
    if (file.isEmpty()) throw new BadRequestException("Empty file");
    if (file.getSize() > 10_000_000) throw new BadRequestException("File too large");

    String contentType = file.getContentType();
    if (!Set.of("image/jpeg", "image/png", "application/pdf").contains(contentType)) {
        throw new BadRequestException("未対応のファイル種別: " + contentType);
    }

    // POINT: ファイル名のサニタイズ（パストラバーサル防止）
    String safeFilename = UUID.randomUUID() + getExtension(file.getOriginalFilename());
    Path target = uploadDir.resolve(safeFilename);

    Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

    return ResponseEntity.ok(new FileMetadata(safeFilename, file.getSize(), contentType));
}