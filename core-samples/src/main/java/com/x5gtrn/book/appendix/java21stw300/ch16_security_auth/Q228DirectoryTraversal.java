package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.nio.file.Path;
public final class Q228DirectoryTraversal implements Sample {
    static final Path BASE = Path.of("/var/www/files").toAbsolutePath().normalize();
    public String chapter(){return "16_Security_Auth";}
    public int problem(){return 228;}
    public String title(){return "ディレクトリトラバーサル対策";}
    public void run(){
        for (String req : new String[]{"report.pdf", "../../etc/shadow", "sub/../ok.txt", "..%2f..%2fpasswd"})
            System.out.printf("%-22s -> %s%n", req, serve(req));
    }
    private String serve(String userPath){
        Path resolved = BASE.resolve(userPath).normalize();      // 正規化してから
        if (!resolved.startsWith(BASE)) return "拒否 (基準ディレクトリ外)"; // 配下か検証
        return "許可 " + resolved;
    }
}
