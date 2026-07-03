package com.x5gtrn.book.appendix.java21stw300.ch17_testing;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
@Component
public class Q252TestSlices implements FrameworkSample {
    public String chapter(){ return "17_Testing"; }
    public int problem(){ return 252; }
    public String title(){ return "Spring Boot テストスライス"; }
    public void run(){
        // スライステストは必要な層だけをロードし、全コンテキスト起動より高速・焦点が明確
        System.out.println("@WebMvcTest   : Controller + MVC 基盤のみ（Service/Repository は @MockBean で差替）");
        System.out.println("@DataJpaTest  : JPA + 組込DB のみ（Web層は載らない・既定でロールバック）");
        System.out.println("@JsonTest     : Jackson シリアライズ周りのみ");
        System.out.println("@SpringBootTest: 全コンテキスト（結合テスト向け・最も重い）");
        System.out.println("使い分け: 単体寄りはスライス、全体結合のみ @SpringBootTest にして高速性と信頼性を両立");
    }
}
