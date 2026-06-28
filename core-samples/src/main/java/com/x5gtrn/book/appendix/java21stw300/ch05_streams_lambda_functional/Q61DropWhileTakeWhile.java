package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.stream.Stream;

/**
 * 問題61: dropWhile は条件が false になるまで先頭を捨て、takeWhile は条件が false になった時点で止める。
 */
public final class Q61DropWhileTakeWhile implements Sample {

    public String chapter() { return "05_Streams_Lambda_Functional"; }
    public int problem()    { return 61; }
    public String title()   { return "dropWhile / takeWhile"; }

    public void run() {
        List<Integer> afterDrop = Stream.of(100, 200, 300, 400, 500, 600)
                .dropWhile(t -> t < 300)
                .toList();
        System.out.println("dropWhile(t<300)              = " + afterDrop);

        List<Integer> result = Stream.of(100, 200, 300, 400, 500, 600)
                .dropWhile(t -> t < 300)
                .takeWhile(t -> t <= 500)
                .toList();
        System.out.println("dropWhile then takeWhile(<=500) = " + result);
    }
}
