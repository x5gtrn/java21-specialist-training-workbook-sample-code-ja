try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
    Path configDir = Path.of("/app/config");

    // POINT: ディレクトリを登録（監視対象イベントを指定）
    configDir.register(watcher,
        StandardWatchEventKinds.ENTRY_CREATE,  // ファイル作成
        StandardWatchEventKinds.ENTRY_MODIFY,  // ファイル変更
        StandardWatchEventKinds.ENTRY_DELETE);  // ファイル削除

    log.info("Watching directory: {}", configDir);

    while (true) {
        WatchKey key = watcher.take();  // POINT: ブロック: イベント発生まで待機
        // ノンブロッキング版: watcher.poll()（即座に返る）
        // タイムアウト版: watcher.poll(10, TimeUnit.SECONDS)

        for (WatchEvent<?> event : key.pollEvents()) {
            WatchEvent.Kind<?> kind = event.kind();

            if (kind == StandardWatchEventKinds.OVERFLOW) {
                continue;  // イベントが多すぎて一部欠落した場合
            }

            Path changedFile = configDir.resolve((Path) event.context());
            log.info("Event: {} on {}", kind.name(), changedFile);

            if (kind == ENTRY_MODIFY && changedFile.toString().endsWith(".properties")) {
                reloadConfig(changedFile);
            }
        }

        boolean valid = key.reset();  // POINT: リセット必須（次のイベント受信のため）
        if (!valid) {
            log.warn("Watch key no longer valid. Directory may have been deleted.");
            break;
        }
    }
}

// POINT: サブディレクトリの監視（自動的ではない → 手動登録が必要）
Files.walkFileTree(rootDir, new SimpleFileVisitor<>() {
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
            throws IOException {
        dir.register(watcher, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
        return FileVisitResult.CONTINUE;
    }
});