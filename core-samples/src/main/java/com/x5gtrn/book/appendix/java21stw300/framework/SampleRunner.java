package com.x5gtrn.book.appendix.java21stw300.framework;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * {@link Sample} を実行し、標準出力／標準エラーへの出力を文字列として捕捉する。
 *
 * <p>{@code System.setOut} はプロセス全体で共有されるため、複数サンプルの同時実行が
 * 互いの出力を取り違えないよう、実行は直列化（synchronized）している。デモ用途では十分。</p>
 */
public final class SampleRunner {

    /** 実行結果。{@code output} は捕捉した出力、{@code error} は例外が発生したか。 */
    public record Result(String output, boolean error, long millis) {}

    private static final Object LOCK = new Object();

    private SampleRunner() {
    }

    public static Result run(Sample sample) {
        synchronized (LOCK) {
            PrintStream originalOut = System.out;
            PrintStream originalErr = System.err;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            PrintStream capture = new PrintStream(buffer, true, StandardCharsets.UTF_8);

            boolean error = false;
            long start = System.nanoTime();
            System.setOut(capture);
            System.setErr(capture);
            try {
                sample.run();
            } catch (Throwable t) {
                error = true;
                t.printStackTrace();
            } finally {
                System.setOut(originalOut);
                System.setErr(originalErr);
                capture.flush();
            }
            long millis = (System.nanoTime() - start) / 1_000_000;
            String output = buffer.toString(StandardCharsets.UTF_8);
            if (output.isEmpty()) {
                output = "(no output)";
            }
            return new Result(output, error, millis);
        }
    }
}
