package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
@Component
public class Q212MultipartUpload implements FrameworkSample {
    public String chapter(){ return "15_REST_API_HTTP"; }
    public int problem(){ return 212; }
    public String title(){ return "マルチパートファイルアップロード"; }
    public void run(){
        // multipart/form-data は境界(boundary)でパートを区切る。Spring は MultipartFile へ抽象化する。
        String boundary = "----WebKitFormBoundaryABC";
        String body = "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"file\"; filename=\"report.csv\"\r\n"
                + "Content-Type: text/csv\r\n\r\n"
                + "id,name\n1,Alice\n"
                + "\r\n--" + boundary + "--\r\n";
        String filename = body.replaceAll("(?s).*filename=\"([^\"]+)\".*", "$1");
        int bytes = body.getBytes(StandardCharsets.UTF_8).length;
        System.out.println("boundary   = " + boundary);
        System.out.println("filename   = " + filename + " / 全体 " + bytes + " bytes");
        System.out.println("Spring: @RequestParam(\"file\") MultipartFile file で受け取り file.getBytes()/getOriginalFilename()");
        System.out.println("注意: 最大サイズ(spring.servlet.multipart.max-file-size)・拡張子/MIME検証でDoS/不正ファイルを防ぐ");
    }
}
