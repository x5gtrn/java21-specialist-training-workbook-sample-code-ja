package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.stream.Stream;

/**
 * 問題57: 無限ストリームは limit で打ち切る。Stream.iterate でフィボナッチ数列を生成。
 */
public final class Q57LimitIterate implements Sample {

    public String chapter() { return "05_Streams_Lambda_Functional"; }
    public int problem()    { return 57; }
    public String title()   { return "Stream.iterate と limit"; }

    public void run() {
        List<Long> fib = Stream.iterate(new long[]{0, 1}, a -> new long[]{a[1], a[0] + a[1]})
                .limit(10)
                .map(a -> a[0])
                .toList();
        System.out.println("fibonacci(10) = " + fib);

        List<Integer> firstThree = Stream.of(1, 2, 3, 4, 5, 6).limit(3).toList();
        System.out.println("limit(3)      = " + firstThree);
    }
}
