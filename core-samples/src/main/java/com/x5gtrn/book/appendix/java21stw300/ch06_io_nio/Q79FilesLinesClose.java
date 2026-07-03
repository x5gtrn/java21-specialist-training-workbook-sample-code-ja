package com.x5gtrn.book.appendix.java21stw300.ch06_io_nio;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
public final class Q79FilesLinesClose implements Sample {
    public String chapter(){return "06_IO_NIO";}
    public int problem(){return 79;}
    public String title(){return "Files.lines と Stream のクローズ";}
    public void run() throws Exception {
        Path tmp = Files.createTempFile("q79", ".txt");
        try {
            Files.write(tmp, List.of("apple", "banana", "avocado", "cherry"));
            // Files.lines はファイルハンドルを開くため try-with-resources で必ず閉じる
            try (Stream<String> lines = Files.lines(tmp)) {
                long a = lines.filter(l -> l.startsWith("a")).count();
                System.out.println("'a' で始まる行数 = " + a);
            }
            System.out.println("Stream を閉じることでファイルハンドルが解放される");
        } finally { Files.deleteIfExists(tmp); }
    }
}
