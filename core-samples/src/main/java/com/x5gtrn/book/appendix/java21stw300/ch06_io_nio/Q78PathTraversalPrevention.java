package com.x5gtrn.book.appendix.java21stw300.ch06_io_nio;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.nio.file.Path;
public final class Q78PathTraversalPrevention implements Sample {
    static final Path BASE = Path.of("/srv/uploads").toAbsolutePath().normalize();
    public String chapter(){return "06_IO_NIO";}
    public int problem(){return 78;}
    public String title(){return "Path 正規化とパストラバーサル防止";}
    public void run(){
        check("report.pdf");
        check("../../etc/passwd");
        check("sub/dir/../ok.txt");
    }
    private void check(String userInput){
        Path resolved = BASE.resolve(userInput).normalize();
        boolean safe = resolved.startsWith(BASE); // BASE 配下かを正規化後に検証
        System.out.printf("%-22s -> %s (%s)%n", userInput, resolved, safe ? "許可" : "拒否: 基準ディレクトリ外");
    }
}
