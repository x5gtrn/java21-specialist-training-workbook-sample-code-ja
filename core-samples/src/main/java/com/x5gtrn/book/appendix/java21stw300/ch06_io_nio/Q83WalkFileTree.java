package com.x5gtrn.book.appendix.java21stw300.ch06_io_nio;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;
public final class Q83WalkFileTree implements Sample {
    public String chapter(){return "06_IO_NIO";}
    public int problem(){return 83;}
    public String title(){return "NIO.2 walkFileTree() によるヒープ最適化";}
    public void run() throws Exception {
        Path root = Files.createTempDirectory("q83");
        Path sub = Files.createDirectory(root.resolve("sub"));
        Files.writeString(root.resolve("a.txt"), "a");
        Files.writeString(sub.resolve("b.txt"), "bb");
        try {
            AtomicInteger files = new AtomicInteger();
            long[] totalBytes = {0};
            // walkFileTree は Visitor で 1 件ずつ処理するため巨大ツリーでもメモリ効率が良い
            Files.walkFileTree(root, new SimpleFileVisitor<>() {
                public FileVisitResult visitFile(Path f, BasicFileAttributes attrs) throws IOException {
                    files.incrementAndGet(); totalBytes[0] += attrs.size(); return FileVisitResult.CONTINUE;
                }
            });
            System.out.println("ファイル数 = " + files.get() + ", 合計サイズ = " + totalBytes[0] + " bytes");
        } finally {
            Files.walkFileTree(root, new SimpleFileVisitor<>() {
                public FileVisitResult visitFile(Path f, BasicFileAttributes a) throws IOException { Files.delete(f); return FileVisitResult.CONTINUE; }
                public FileVisitResult postVisitDirectory(Path d, IOException e) throws IOException { Files.delete(d); return FileVisitResult.CONTINUE; }
            });
        }
    }
}
