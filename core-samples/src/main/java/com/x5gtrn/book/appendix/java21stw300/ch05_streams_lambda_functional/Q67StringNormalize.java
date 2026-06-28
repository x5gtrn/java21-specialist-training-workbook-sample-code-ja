package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.function.Function;

/**
 * 問題67: 関数合成（Function.andThen）による文字列正規化パイプライン。
 * " hello " -> strip -> toUpperCase -> length。
 */
public final class Q67StringNormalize implements Sample {

    public String chapter() { return "05_Streams_Lambda_Functional"; }
    public int problem()    { return 67; }
    public String title()   { return "Function 合成による文字列正規化"; }

    public void run() {
        Function<String, String> normalize = ((Function<String, String>) String::strip)
                .andThen(String::toUpperCase);

        for (String raw : List.of(" hello ", "  Java21  ", "x")) {
            String normalized = normalize.apply(raw);
            System.out.printf("\"%s\" -> \"%s\" -> length=%d%n", raw, normalized, normalized.length());
        }
    }
}
