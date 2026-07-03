package com.x5gtrn.book.appendix.java21stw300.ch17_testing;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
@Component
public class Q202TestContextCache implements FrameworkSample {
    public String chapter(){ return "17_Testing"; }
    public int problem(){ return 202; }
    public String title(){ return "テストコンテキストのキャッシュと @MockBean"; }
    public void run(){
        // Spring TestContext は同一構成の ApplicationContext をキャッシュし、テスト間で再利用して高速化する
        System.out.println("同じ構成のテストは ApplicationContext を再利用（起動コスト削減）");
        System.out.println("キャッシュキー: @ContextConfiguration/@ActiveProfiles/@MockBean 等の組み合わせ");
        System.out.println("@MockBean を使うとコンテキストが変化し別キャッシュに → コンテキスト再生成でテストが遅くなる");
        System.out.println("対策: @MockBean の乱用を避ける / 構成のバリエーションを絞る / スライステストを活用する");
    }
}
