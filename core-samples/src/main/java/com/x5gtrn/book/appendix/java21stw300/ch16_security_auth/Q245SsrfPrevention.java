package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.net.URI;
import java.util.Set;
public final class Q245SsrfPrevention implements Sample {
    static final Set<String> ALLOWED_HOSTS = Set.of("api.example.com", "cdn.example.com");
    public String chapter(){ return "16_Security_Auth"; }
    public int problem(){ return 245; }
    public String title(){ return "SSRF 対策"; }
    public void run(){
        // サーバが受け取ったURLへリクエストする機能は、内部資源(メタデータ/内部API)への到達に悪用される
        for (String url : new String[]{
                "https://api.example.com/data",
                "http://169.254.169.254/latest/meta-data/",   // クラウドメタデータ(危険)
                "http://localhost:8080/admin",
                "http://10.0.0.5/internal"})
            System.out.printf("%-45s -> %s%n", url, check(url));
        System.out.println("対策: 送信先ホストの許可リスト・内部IP帯(127./10./172.16-31./192.168./169.254.)の拒否");
        System.out.println("注意: DNSリバインディング対策として、名前解決後のIPで再検証し、リダイレクトも制限する");
    }
    private String check(String url){
        try {
            String host = URI.create(url).getHost();
            if (host == null) return "拒否(不正URL)";
            if (host.equals("localhost") || host.startsWith("127.") || host.startsWith("10.")
                    || host.startsWith("192.168.") || host.startsWith("169.254."))
                return "拒否(内部/リンクローカル)";
            return ALLOWED_HOSTS.contains(host) ? "許可(許可リスト内)" : "拒否(許可リスト外)";
        } catch (RuntimeException e) { return "拒否(パース失敗)"; }
    }
}
