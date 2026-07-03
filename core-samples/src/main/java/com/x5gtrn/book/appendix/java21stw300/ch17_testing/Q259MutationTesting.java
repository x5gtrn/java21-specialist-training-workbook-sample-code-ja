package com.x5gtrn.book.appendix.java21stw300.ch17_testing;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.function.IntPredicate;
public final class Q259MutationTesting implements Sample {
    public String chapter(){return "17_Testing";}
    public int problem(){return 259;}
    public String title(){return "Mutation Testing の目的";}
    public void run(){
        IntPredicate original = age -> age >= 18;        // 本来の実装
        IntPredicate mutant   = age -> age > 18;         // 「>= を >」に変異させたミュータント
        // 弱いテスト（境界18を検証しない）は、原本もミュータントも通過してしまう
        boolean weakOnOriginal = weakTest(original), weakOnMutant = weakTest(mutant);
        System.out.println("弱いテスト: 原本=" + weakOnOriginal + ", ミュータント=" + weakOnMutant
                + " → 両方PASS = ミュータント生存（テスト不十分）");
        // 境界値18を追加した強いテストはミュータントを「殺す」
        boolean strongOnOriginal = strongTest(original), strongOnMutant = strongTest(mutant);
        System.out.println("強いテスト: 原本=" + strongOnOriginal + ", ミュータント=" + strongOnMutant
                + " → ミュータントFAIL = 検出成功");
        System.out.println("変異検査はテストの『抜け』を炙り出し、カバレッジ率だけでは見えない品質を測る");
    }
    private boolean weakTest(IntPredicate p){ return p.test(20) && !p.test(10); }
    private boolean strongTest(IntPredicate p){ return p.test(20) && !p.test(10) && p.test(18); }
}
