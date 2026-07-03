package com.x5gtrn.book.appendix.java21stw300.ch06_io_nio;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;
public final class Q81WatchService implements Sample {
    public String chapter(){return "06_IO_NIO";}
    public int problem(){return 81;}
    public String title(){return "NIO.2 WatchService によるファイル監視";}
    public void run() throws Exception {
        Path dir = Files.createTempDirectory("q81");
        try (WatchService watch = FileSystems.getDefault().newWatchService()) {
            dir.register(watch, StandardWatchEventKinds.ENTRY_CREATE);
            Files.writeString(dir.resolve("new-file.txt"), "hi"); // 監視対象の変更を発生させる
            WatchKey key = watch.poll(3, TimeUnit.SECONDS);
            if (key != null) {
                for (WatchEvent<?> e : key.pollEvents())
                    System.out.println("検出: " + e.kind().name() + " -> " + e.context());
                key.reset();
            } else {
                System.out.println("イベント未検出（環境依存でタイムアウト）");
            }
        } finally {
            Files.deleteIfExists(dir.resolve("new-file.txt"));
            Files.deleteIfExists(dir);
        }
    }
}
