package com.x5gtrn.book.appendix.java21stw300.ch06_io_nio;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
public final class Q80ByteBufferChannel implements Sample {
    public String chapter(){ return "06_IO_NIO"; }
    public int problem(){ return 80; }
    public String title(){ return "ByteBuffer とチャネル"; }
    public void run() throws Exception {
        Path tmp = Files.createTempFile("q80", ".bin");
        try {
            ByteBuffer buf = ByteBuffer.allocate(64);
            buf.put("Hello NIO".getBytes(StandardCharsets.UTF_8));  // 書込でposition前進
            System.out.println("書込後  position=" + buf.position() + ", limit=" + buf.limit());
            buf.flip();                                             // 読み取り用に position=0, limit=旧position
            System.out.println("flip後  position=" + buf.position() + ", limit=" + buf.limit());
            try (FileChannel ch = FileChannel.open(tmp, StandardOpenOption.WRITE)) {
                ch.write(buf);
            }
            // 読み戻し
            ByteBuffer rb = ByteBuffer.allocate(64);
            try (FileChannel ch = FileChannel.open(tmp, StandardOpenOption.READ)) {
                ch.read(rb);
            }
            rb.flip();
            byte[] out = new byte[rb.remaining()]; rb.get(out);
            System.out.println("読戻し  = " + new String(out, StandardCharsets.UTF_8));
            System.out.println("要点: 書込→flip()→読取。clear()で全再利用、compact()で未読分を残す");
        } finally { Files.deleteIfExists(tmp); }
    }
}
