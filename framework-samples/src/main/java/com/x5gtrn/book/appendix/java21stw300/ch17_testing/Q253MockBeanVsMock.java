package com.x5gtrn.book.appendix.java21stw300.ch17_testing;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
@Component
public class Q253MockBeanVsMock implements FrameworkSample {
    interface PricingClient { int price(String sku); }
    public String chapter(){ return "17_Testing"; }
    public int problem(){ return 253; }
    public String title(){ return "@MockBean vs @Mock"; }
    public void run(){
        // 手製フェイクで「依存を差し替える」概念を示す（Mockito の @Mock 相当）
        PricingClient fake = sku -> "A".equals(sku) ? 100 : 0;
        System.out.println("フェイク差替の戻り値: price(A)=" + fake.price("A") + ", price(B)=" + fake.price("B"));
        System.out.println("@Mock     : Mockito が生成する純粋なモック。Springコンテキストとは無関係（単体テスト向け）");
        System.out.println("@MockBean : Springコンテキスト内の該当Beanをモックで置換（スライス/結合テスト向け）");
        System.out.println("注意: @MockBean はコンテキストを変えるためテストキャッシュが効きにくくなる");
    }
}
