package com.x5gtrn.book.appendix.java21stw300.ch17_testing;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q248ParameterizedTest implements Sample {
    record Case(int input, boolean expectedPrime) {}
    public String chapter(){ return "17_Testing"; }
    public int problem(){ return 248; }
    public String title(){ return "JUnit 5 パラメータ化テスト"; }
    public void run(){
        // 1つのロジックを複数入力で検証する（@ParameterizedTest の考え方を手続きで再現）
        List<Case> cases = List.of(new Case(2,true), new Case(3,true), new Case(4,false),
                                   new Case(9,false), new Case(13,true), new Case(1,false));
        int pass = 0;
        for (Case c : cases) {
            boolean actual = isPrime(c.input());
            boolean ok = actual == c.expectedPrime();
            System.out.printf("  isPrime(%2d)=%-5s 期待=%-5s %s%n", c.input(), actual, c.expectedPrime(), ok ? "OK" : "NG");
            if (ok) pass++;
        }
        System.out.println("結果: " + pass + "/" + cases.size() + " ケース成功");
        System.out.println("JUnit5: @ParameterizedTest + @ValueSource/@CsvSource/@MethodSource で表形式テストを簡潔に書く");
    }
    private boolean isPrime(int n){
        if (n < 2) return false;
        for (int i = 2; (long)i*i <= n; i++) if (n % i == 0) return false;
        return true;
    }
}
