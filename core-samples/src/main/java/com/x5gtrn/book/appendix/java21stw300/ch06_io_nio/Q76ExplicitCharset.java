package com.x5gtrn.book.appendix.java21stw300.ch06_io_nio;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
public final class Q76ExplicitCharset implements Sample {
    public String chapter(){return "06_IO_NIO";}
    public int problem(){return 76;}
    public String title(){return "文字コードを明示する I/O";}
    public void run() throws Exception {
        Path tmp = Files.createTempFile("q76", ".txt");
        try {
            String text = "café";
            Files.write(tmp, text.getBytes(StandardCharsets.UTF_8));
            byte[] raw = Files.readAllBytes(tmp);
            System.out.println("UTF-8 バイト列 = " + Arrays.toString(raw));
            System.out.println("UTF-8 で復元  = " + new String(raw, StandardCharsets.UTF_8));
            // 誤ったエンコーディングで読むと文字化けする。常に明示するのが安全。
        } finally { Files.deleteIfExists(tmp); }
    }
}
