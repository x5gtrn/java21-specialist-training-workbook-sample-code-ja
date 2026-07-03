// NG: Files.walk() + collect() → 全パスをメモリに蓄積
List<Path> allCsvFiles = Files.walk(rootDir)  // POINT: 全 Path を遅延生成
    .filter(p -> p.toString().endsWith(".csv"))
    .collect(Collectors.toList());  // POINT: リストに全蓄積 → 数百万 Path オブジェクト
for (Path csv : allCsvFiles) {
    processFile(csv);
}
// → 100万ファイル → 100万の Path + 100万の String (toString) がヒープに同時存在
// → GC 大量発生、OOM リスク

// OK: walkFileTree + FileVisitor → 逐次処理、メモリ最小
AtomicLong processedCount = new AtomicLong(0);
Files.walkFileTree(rootDir, new SimpleFileVisitor<>() {
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (file.toString().endsWith(".csv")) {
            processFile(file);  // POINT: 即座に処理、パスを保持しない
            processedCount.incrementAndGet();
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        log.warn("Cannot access: {}", file, exc);
        return FileVisitResult.CONTINUE;  // POINT: エラーでも走査継続
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        if (dir.getFileName().toString().startsWith(".")) {
            return FileVisitResult.SKIP_SUBTREE;  // POINT: 隠しディレクトリをスキップ
        }
        return FileVisitResult.CONTINUE;
    }
});
// → 同時にメモリに存在するのは現在処理中の Path のみ
// → ディレクトリスタック深さ分の Path のみ（通常数十程度）

// ■ FileVisitResult の制御オプション
// CONTINUE     → 走査を通常通り継続
// TERMINATE    → 走査全体を即座に終了（目的のファイルが見つかった場合等）
// SKIP_SUBTREE → 現在のディレクトリのサブディレクトリをスキップ
// SKIP_SIBLINGS → 現在のディレクトリの残りのエントリをスキップして親に戻る