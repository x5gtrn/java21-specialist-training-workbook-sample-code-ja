package com.x5gtrn.book.appendix.java21stw300.ch06_io_nio;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
public final class Q84FileChannelMemoryMapped implements Sample {
    public String chapter(){ return "06_IO_NIO"; }
    public int problem(){ return 84; }
    public String title(){ return "FileChannel とメモリマップド I/O"; }
    public void run() throws Exception {
        Path tmp = Files.createTempFile("q84", ".bin");
        try {
            Files.write(tmp, "AAAAAAAAAA".getBytes(StandardCharsets.UTF_8));
            try (FileChannel ch = FileChannel.open(tmp, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
                // ファイルをメモリに直接マップ。バッファ書換が(OS経由で)ファイルへ反映される
                MappedByteBuffer map = ch.map(FileChannel.MapMode.READ_WRITE, 0, ch.size());
                map.put(0, (byte) 'Z');           // 先頭バイトを書換
                map.put(9, (byte) 'Z');
                System.out.println("マップ経由で書換 (先頭/末尾を Z に)");
            }
            System.out.println("ファイル内容 = " + Files.readString(tmp));
            System.out.println("利点: read/write システムコールとバッファコピーを介さず大容量を高速処理");
            System.out.println("注意: マップ解除の制御が難しく、巨大ファイル/多用時はリソース管理に留意");
        } finally { Files.deleteIfExists(tmp); }
    }
}
