try (FileChannel channel = FileChannel.open(Path.of("large-log.dat"), StandardOpenOption.READ)) {
    long fileSize = channel.size();

    // 10GB ファイルの場合、複数のセグメントに分割してマッピング
    // MappedByteBuffer.map() の size は int（最大約2GB）
    long offset = 0;
    int segmentSize = Integer.MAX_VALUE;  // 約2GB

    while (offset < fileSize) {
        long remaining = fileSize - offset;
        int mapSize = (int) Math.min(remaining, segmentSize);

        MappedByteBuffer mappedBuffer = channel.map(
            FileChannel.MapMode.READ_ONLY,  // 読み取り専用
            offset,
            mapSize
        );

        // POINT: ヒープ外の仮想メモリにマッピング
        // OS がページングを管理、必要な部分のみ物理メモリにロード
        processBuffer(mappedBuffer);

        offset += mapSize;
    }
}