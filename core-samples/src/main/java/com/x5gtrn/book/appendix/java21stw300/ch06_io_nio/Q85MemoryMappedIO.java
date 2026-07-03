package com.x5gtrn.book.appendix.java21stw300.ch06_io_nio;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
public final class Q85MemoryMappedIO implements Sample {
    public String chapter(){return "06_IO_NIO";}
    public int problem(){return 85;}
    public String title(){return "メモリマップド I/O の適用判断";}
    public void run() throws Exception {
        Path tmp = Files.createTempFile("q85", ".dat");
        try (FileChannel ch = FileChannel.open(tmp, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            MappedByteBuffer mbb = ch.map(FileChannel.MapMode.READ_WRITE, 0, 16);
            mbb.putInt(0, 100).putInt(4, 200).putInt(8, 300); // メモリ経由で直接書き込み
            System.out.println("mapped[0]=" + mbb.getInt(0) + ", mapped[4]=" + mbb.getInt(4) + ", mapped[8]=" + mbb.getInt(8));
            // 大きなファイルへのランダムアクセスに有効。小さな逐次 I/O ではオーバーヘッドが上回る。
        } finally { Files.deleteIfExists(tmp); }
    }
}
