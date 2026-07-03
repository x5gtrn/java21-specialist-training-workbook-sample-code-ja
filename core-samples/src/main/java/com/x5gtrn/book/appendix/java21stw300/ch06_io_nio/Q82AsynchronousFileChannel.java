package com.x5gtrn.book.appendix.java21stw300.ch06_io_nio;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;
public final class Q82AsynchronousFileChannel implements Sample {
    public String chapter(){return "06_IO_NIO";}
    public int problem(){return 82;}
    public String title(){return "AsynchronousFileChannel の性能改善";}
    public void run() throws Exception {
        Path tmp = Files.createTempFile("q82", ".dat");
        try (AsynchronousFileChannel ch = AsynchronousFileChannel.open(tmp, StandardOpenOption.WRITE)) {
            ByteBuffer buffer = ByteBuffer.wrap("非同期で書き込むデータ".getBytes(StandardCharsets.UTF_8));
            Future<Integer> op = ch.write(buffer, 0); // 非同期書き込み
            int written = op.get();                    // 完了を待機
            System.out.println("書き込みバイト数 = " + written);
        }
        System.out.println("読み戻し = " + Files.readString(tmp));
        Files.deleteIfExists(tmp);
    }
}
