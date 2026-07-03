package com.x5gtrn.book.appendix.java21stw300.ch06_io_nio;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.nio.file.Path;
public final class Q77PathOperations implements Sample {
    public String chapter(){return "06_IO_NIO";}
    public int problem(){return 77;}
    public String title(){return "Path の操作と正規化";}
    public void run(){
        Path base = Path.of("/home/user/docs");
        Path resolved = base.resolve("../config/app.conf");
        System.out.println("resolve      = " + resolved);
        System.out.println("normalize    = " + resolved.normalize());
        System.out.println("getFileName  = " + resolved.getFileName());
        System.out.println("getParent    = " + base.getParent());
        System.out.println("relativize   = " + base.relativize(Path.of("/home/user/docs/a/b.txt")));
    }
}
