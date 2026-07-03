package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.Optional;
public final class Q63StreamReduceReturn implements Sample {
    public String chapter(){ return "05_Streams_Lambda_Functional"; }
    public int problem(){ return 63; }
    public String title(){ return "Stream.reduce() の戻り値"; }
    public void run(){
        List<Integer> nums = List.of(1, 2, 3, 4);
        // 1引数版: 初期値なし -> Optional<T>（空ストリームを表現できる）
        Optional<Integer> sum1 = nums.stream().reduce(Integer::sum);
        Optional<Integer> empty = List.<Integer>of().stream().reduce(Integer::sum);
        System.out.println("reduce(BinaryOperator) -> Optional: " + sum1 + " / 空: " + empty);
        // 2引数版: 初期値あり -> T（空でも初期値を返す）
        int sum2 = nums.stream().reduce(0, Integer::sum);
        System.out.println("reduce(identity, acc) -> T: " + sum2);
        // 3引数版: 並列時の結合関数付き（型変換・並列集約）
        int totalLen = List.of("ab", "cde").stream().reduce(0, (acc, s) -> acc + s.length(), Integer::sum);
        System.out.println("reduce(identity, acc, combiner) -> T: " + totalLen);
    }
}
