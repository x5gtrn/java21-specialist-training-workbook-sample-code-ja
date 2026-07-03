package com.x5gtrn.book.appendix.java21stw300.ch06_io_nio;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.nio.file.Files;
import java.nio.file.Path;
public final class Q75FilesReadWriteString implements Sample {
    public String chapter(){return "06_IO_NIO";}
    public int problem(){return 75;}
    public String title(){return "Files.readString / writeString";}
    public void run() throws Exception {
        Path tmp = Files.createTempFile("q75", ".txt");
        try {
            Files.writeString(tmp, "こんにちは\nJava 21");
            String content = Files.readString(tmp);
            System.out.println("読み込み結果:\n" + content);
        } finally { Files.deleteIfExists(tmp); }
    }
}
