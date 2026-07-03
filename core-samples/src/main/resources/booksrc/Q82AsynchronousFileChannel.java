// ■ 方式1: Future（結果が必要な時に取得）
AsynchronousFileChannel channel = AsynchronousFileChannel.open(
    logPath,
    StandardOpenOption.WRITE,
    StandardOpenOption.CREATE,
    StandardOpenOption.APPEND);

ByteBuffer buffer = ByteBuffer.wrap(logEntry.getBytes(StandardCharsets.UTF_8));

Future<Integer> future = channel.write(buffer, channel.size());
// POINT: 呼び出しスレッドは即座に return → ブロックしない

doOtherImportantWork();  // I/O 完了を待たずに他の処理を実行

int bytesWritten = future.get();  // POINT: 結果が必要になった時点で取得（ここでブロック）
log.debug("Wrote {} bytes to log file", bytesWritten);

// ■ 方式2: CompletionHandler（コールバック通知）
channel.write(buffer, channel.size(), null, new CompletionHandler<Integer, Void>() {
    @Override
    public void completed(Integer bytesWritten, Void attachment) {
        log.debug("Async write complete: {} bytes", bytesWritten);
        // POINT: I/O 完了時に自動呼び出し（別スレッドで実行）
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        log.error("Async write failed", exc);
        // POINT: I/O エラー時に自動呼び出し
    }
});
// POINT: 呼び出しスレッドは即座に次の処理へ

// ■ 同期 vs 非同期の使い分け
// FileChannel (同期): 単発の小ファイル操作、シンプルなスクリプト
// AsynchronousFileChannel (非同期): 大量並行 I/O、サーバーアプリケーション
// → Java 21 の仮想スレッドにより、ブロッキング I/O のコスト構造が変化し、
//    「スレッドを占有しないために非同期 API を使う」という動機は弱まる。
//    ただし通常ファイルの I/O はソケットほど効果が出にくく、ネイティブ呼び出しで
//    キャリアスレッドにピン留めされる場合もあるため、効果は測定して確認する。